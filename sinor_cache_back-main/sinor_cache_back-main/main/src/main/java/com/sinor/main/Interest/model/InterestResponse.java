package com.sinor.main.Interest.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestResponse {
    private boolean ok;
    private List<Interest> data;

}
