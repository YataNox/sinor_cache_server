package com.sinor.cache.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainCacheRequset {

    private Map<String, String> Requsetbody;
}
