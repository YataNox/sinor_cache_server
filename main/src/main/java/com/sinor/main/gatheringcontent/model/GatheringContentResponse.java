package com.sinor.main.gatheringcontent.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GatheringContentResponse {
    private boolean ok;
    private List<GatheringContent> data;

}
