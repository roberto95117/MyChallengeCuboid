package co.fullstacklabs.cuboid.challenge.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuboidDTO {
    private Long id;

    @NotNull(message = "Cuboid width can't be null.")
    private Float width;

    @NotNull(message = "Cuboid height can't be null.")
    private Float height;

    @NotNull(message = "Cuboid depth can't be null.")
    private Float depth;

    @NotNull(message = "Cuboid volume can't be null.")
    private Double volume;

    @NotNull(message = "Cuboid related bag can't be null.")
    private Long bagId;
    
    public Double getVolume() {
    	return Double.valueOf( this.height * this.width * this.depth);
    }
    
    @Builder
    public static CuboidDTO cuboidDTO(Long id, float width, float height, float depth, Long bagId, Double volume) {
    	CuboidDTO dto = new CuboidDTO();
    	dto.setId(id);
    	dto.setWidth(width);
    	dto.setHeight(height);
    	dto.setDepth(depth);
    	dto.setBagId(bagId);
    	dto.setVolume(volume);
    	return dto;
    }
}
