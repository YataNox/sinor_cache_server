package com.sinor.stomp.vote.common;

import java.io.Serializable;
import java.util.NoSuchElementException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractCrudService<ResponseDto, RequestDto, Repository extends JpaRepository<Entity, IdType>, Entity extends BaseEntity<IdType>, IdType extends Serializable> {

    protected final Repository repository;

    protected AbstractCrudService(Repository repository) {
        this.repository = repository;
    }

    protected abstract Entity fromRequestDtoToEntity(RequestDto requestDto);

    protected abstract ResponseDto fromEntitytoResponseDto(Entity entity);

    public abstract ResponseDto updateObject(IdType id, RequestDto requestDto);


    public ResponseDto createObject(RequestDto requestDto) {
        Entity e = repository.save(fromRequestDtoToEntity(requestDto));
        return fromEntitytoResponseDto(e);
    }

    public ResponseDto createObject(Entity entity) {
        Entity e = repository.save(entity);
        return fromEntitytoResponseDto(e);
    }

    public ResponseDto readObject(IdType id) throws NoSuchElementException {
        Entity e = repository.findById(id).orElseThrow();
        return fromEntitytoResponseDto(e);
    }

    public ResponseDto deleteObject(IdType id) throws NoSuchElementException {
        Entity e = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return fromEntitytoResponseDto(e);
    }
}