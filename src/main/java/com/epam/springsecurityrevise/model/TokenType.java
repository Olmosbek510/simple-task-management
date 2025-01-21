package com.epam.springsecurityrevise.model;

import com.epam.springsecurityrevise.model.enums.TokenTypeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenType {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TokenTypeName tokenTypeName;
}
