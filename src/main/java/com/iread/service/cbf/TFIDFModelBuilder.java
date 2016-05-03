package com.iread.service.cbf;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.grouplens.lenskit.vectors.MutableSparseVector;
import org.grouplens.lenskit.vectors.SparseVector;
import org.grouplens.lenskit.vectors.VectorEntry;
import org.lenskit.inject.Transient;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Builder for computing {@linkplain TFIDFModel TF-IDF models} from item tag data.  Each item is
 * represented by a normalized TF-IDF vector.
 *
 * @author <a href="http://www.grouplens.org">GroupLens Research</a>
 */
public class TFIDFModelBuilder implements Provider<TFIDFModel> {
    private final ItemTagDAO dao;

    /**
     * Construct a model builder.  The {@link Inject} annotation on this constructor tells LensKit
     * that it can be used to build the model builder.
     *
     * @param dao The item-tag DAO.  This is where the builder will get access to items and their
     *            tags.
     *            <p>{@link Transient} means that the provider promises that the DAO is no longer
     *            needed once the object is built (that is, the model will not contain a reference
     *            to the DAO).  This allows LensKit to configure your recommender components
     *            properly.  It's up to you to keep this promise.</p>
     */
    @Inject
    public TFIDFModelBuilder(@Transient ItemTagDAO dao) {
        this.dao = dao;
    }

    /**
     * This method is where the model should actually be computed.
     * @return The TF-IDF model (a model of item tag vectors).
     */
    @Override
    public TFIDFModel get() {
        // Постоим ассоциативный массив тэгов и их идентификаторам.
        // Присвоим строковым тегам идентификаторы чтобы использвать их как набор ключей
        Map<String, Long> tagIds = buildTagIdMap();

        // Создаём вектор для накопления частот вхождений тэгов документа для IDF подсчёта
        MutableSparseVector docFreq = MutableSparseVector.create(tagIds.values());
        docFreq.fill(0);

        // Пройдём два шага. Первй, построим TF-вектор для каждого элемента.
        // Пока мы это делаем, строим TF вектор.
        // Будем применять IDF для кадого TF вектора и нормализировать его в одномерный вектор.

        // Созданим ассоциативный массив для хранения TF векторов
        Map<Long,MutableSparseVector> itemVectors = Maps.newHashMap();

        // Создадим рабочий вектор для накопления каждого тега вектора элементов
        // Вектор будет повторно использован для каждого элемента
        MutableSparseVector work = MutableSparseVector.create(tagIds.values());

        // Идём по элементоа вычисляя для каждого вектор элементов
        LongSet items = dao.getItemIds();
        long counter = 0;
        for (long item: items) {
            counter++;
            // Очищаем рабочий вектор для тэгов текущего элемента
            work.clear();
            // Теперь вектор пуст!

            // Публикуем с числом вхождений тегов для кажлого элемента
            List<String> tags =  dao.getItemTags(item);
            for(String tag: tags){
                // В tagIds мы находим как много вхождений тега в элемент
                long tagId = tagIds.get(tag);

                // Проверяем что tagId уже существует, если так - инкрементируем, нет - инициализируем в единицу
                try{
                    work.set(tagId, work.get(tagId)+1);
                }catch(IllegalArgumentException e){
                    // выставляем в единицу
                    work.set(tagId, 1);
                }
            }

            // Создаём другой временный вектор для поиска что тэг уже был использован для этого лемента
            MutableSparseVector temp = MutableSparseVector.create(tagIds.values());

            for(String tag: tags){
                long tagId = tagIds.get(tag);
                try{
                    // Мы уже видели этот тег в этом элементе
                    temp.get(tagId);
                    continue;
                }catch(IllegalArgumentException e){
                    // Впервые видем этот тэг в элементе
                    temp.set(tagId, 1);
                    docFreq.set(tagId, docFreq.get(tagId)+1);
                }

            }


            // Сохраняем сокращённую копию вектора (хранищий только тэги для этого элемента) в наш массив,
            // будет добавлен в IDF и нормализирован после
            itemVectors.put(item, work.shrinkDomain());
            // work готов для очистки и повторого использования для следующего элемента
        }

        // Просматриваем все элементы, т.к. мы имеем TF вектор длч каждого элемента и глобальный вектор для частоты вхождений
        // Согласно формуле, инвертируем и берём логарифм по частоте. Можем делать это совместно.
        for (VectorEntry e: docFreq.fast()) {
            long tagId = e.getKey();
            if(tagId ==1){
                System.out.println("");
            }
            double idf = e.getValue();
            double log_idf = Math.log10(counter/idf);
            docFreq.set(tagId, log_idf);
        }

        // Теперь docFreq - логарифмированный IDF вектор
        // Теперь мы можем его использовать в IDF для каждого элемента вектора и положить его в финальную модель
        // Создадим массив для хранения финальной модели
        Map<Long,SparseVector> modelData = Maps.newHashMap();
        for (Map.Entry<Long,MutableSparseVector> entry: itemVectors.entrySet()) {
            // tv - TF каждого тега для элемента
            MutableSparseVector tv = entry.getValue();
            // Конвертируем этот вектор в TF-IDF вектор
            for(VectorEntry v: tv.fast()){
                double tf = v.getValue();
                double idf = docFreq.get(v.getKey());
                tv.set(v.getKey(), tf*idf);
            }
            // tv теперь - tf*idf вектор

            // Нормализируем TF-IDF вектор в одномерный вектор
            // Метод tv.norm() получает Евклидову длину вектора
            double len = tv.norm();

            for(VectorEntry v: tv.fast()){
                tv.set(v.getKey(), v.getValue()/len);
            }
            
            // Сохраним неизменную весию вектора в данных модели
            modelData.put(entry.getKey(), tv.freeze());
        }

        // Нам больше не нужен IDF вектор так как мы не получим новых тегов
        return new TFIDFModel(tagIds, modelData);
    }

    /**
     * Build a mapping of tags to numeric IDs.
     *
     * @return A mapping from tags to IDs.
     */
    private Map<String,Long> buildTagIdMap() {
        // Get the universe of all tags
        Set<String> tags = dao.getTagVocabulary();
        // Allocate our new tag map
        Map<String,Long> tagIds = Maps.newHashMap();

        for (String tag: tags) {
            // Map each tag to a new number.
            tagIds.put(tag, tagIds.size() + 1L);
        }
        return tagIds;
    }
}
