package com.kh.simdo.common.util.jpa;

import java.util.Map;

public class EntityUtilsBuilder<T> {

    protected T entity;
    protected Map<String, Object> map;

    public EntityUtilsBuilder<T> entity(T entity) {
        this.entity = entity;
        return this;
    }

    public EntityUtilsBuilder<T> map(Map<String, Object> map) {
        this.map = map;
        return this;
    }

    // 빌더만들기
    public EntityUtils<T> build(){
        return new EntityUtils<T>(this);
    }
}
