package org.pinguweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ZoneDTO implements DTO{
    private int ID;
    private String name;
    private String description;
    private String emergencyLevel;
    private Integer catastrophe;
    private List<Integer> storages;
    private List<Double> latitudes;
    private List<Double> longitudes;
}
