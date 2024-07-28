package me.ankanroychowdhury.scm.dtos;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskResponseDTO {
    Map<String, TaskResponseDTO> tasks;
}
