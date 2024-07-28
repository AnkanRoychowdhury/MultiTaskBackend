package me.ankanroychowdhury.scm.dtos;

import com.google.gson.JsonArray;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignRequestDTO {
    List<String> emailsToAssign;
}
