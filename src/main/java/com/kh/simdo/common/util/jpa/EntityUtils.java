package com.kh.simdo.common.util.jpa;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class EntityUtils<T> {

    private T entity;
    private Map<String, Object> map;

    public EntityUtils(EntityUtilsBuilder<T> builder) {
        this.entity = builder.entity;
        this.map = builder.map;
    }

    // 옮겨주는 클래스
    public T mergeEntityWithMap() {
        // entity의 모든 필드를 필드배열로 받아준다
        // 모든 필드를 접근제한자에 상관없이 접근가능한 상태로 만들어 준다.
        Field[] entityFields = getFields(entity);
        BeanUtilsBean beanUtil = new BeanUtilsBean();
        try {
            for (Field field : entityFields) {
                if (map.keySet().contains(field.getName())) { // 필드이름이 키값에 있는지 없는지 검증이 가능. vo를 map으로썻을때의 장점이다.
                    beanUtil.copyProperty(entity, field.getName(), map.get(field.getName()));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ToAlertException(ErrorCode.CODE_500, e);
        }

        return entity;
    }

    private Field[] getFields(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields(); // entity의 모든 필드를 필드배열로 받아준다. getDeclaredFields 모든 선언된
        // 필드들 가져온다.
        for (Field field : fields) {
            field.setAccessible(true); // 모든 필드를 접근제한자에 상관없이 접근가능한 상태로 만들어 준다.
        }

        return fields;
    }

}
