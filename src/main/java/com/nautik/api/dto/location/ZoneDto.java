package com.nautik.api.dto.location;

import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import com.nautik.api.dto.port.PortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto implements Serializable {
    Integer id;
    String name;
    String description;
    String portName;
   List<String> mooringCategoriesMooringNumber;
}