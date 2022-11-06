package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.dto.SusComputedResult;

public interface QuestionaryResultsRepo extends CrudRepository<SusComputedResult, Integer>
{

}
