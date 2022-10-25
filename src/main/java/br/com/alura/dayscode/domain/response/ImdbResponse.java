package br.com.alura.dayscode.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ImdbResponse {

    public List<Item> items;

}

