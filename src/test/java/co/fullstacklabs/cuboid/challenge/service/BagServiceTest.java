package co.fullstacklabs.cuboid.challenge.service;

import co.fullstacklabs.cuboid.challenge.dto.BagDTO;
import co.fullstacklabs.cuboid.challenge.dto.NewBagDTO;
import co.fullstacklabs.cuboid.challenge.exception.ResourceNotFoundException;
import co.fullstacklabs.cuboid.challenge.model.Bag;
import co.fullstacklabs.cuboid.challenge.repository.BagRepository;
import co.fullstacklabs.cuboid.challenge.service.impl.BagServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import testbuilders.BagTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BagServiceTest {
    @InjectMocks
    private BagServiceImpl bagService;
    @Mock
    private BagRepository repository;
    @Mock
    private ModelMapper mapper;

    @Test
    void testCreatingSuccess() {
        String title = "Title";
        double volume = 20d;
        Bag bag = BagTestBuilder.builder().title(title).volume(volume).build();
        Mockito.when(repository.save(Mockito.any(Bag.class))).thenReturn(bag);

        bagService.create(NewBagDTO.builder().title(title).volume(volume).build());

        ArgumentCaptor<Bag> bagCaptor = ArgumentCaptor.forClass(Bag.class);
        Mockito.verify(repository).save(bagCaptor.capture());
        Mockito.verify(mapper).map(bag, BagDTO.class);

        assertEquals(title, bagCaptor.getValue().getTitle());
        assertEquals(volume, bagCaptor.getValue().getVolume());

    }

    @Test
    void testGetAll() {
        String title = "Title 1";
        float volume = 20f;
        String title2 = "Title 2";
        float volume2 = 30f;
        Bag bag1 = BagTestBuilder.builder().title(title).volume(volume).build();
        Bag bag2 = BagTestBuilder.builder().title(title2).volume(volume2).build();
        List<Bag> bagList = Lists.newArrayList(bag1, bag2);
        Mockito.when(repository.findAll()).thenReturn(bagList);

        bagService.getAll();

        Mockito.verify(repository).findAll();
        Mockito.verify(mapper).map(bagList.get(0), BagDTO.class);
        Mockito.verify(mapper).map(bagList.get(1), BagDTO.class);
    }

    @Test
    void testFindByIdSuccess() {
        long id = 1L;
        Bag bag1 = BagTestBuilder.builder().build();
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(bag1));
        bagService.findById(id);
        Mockito.verify(repository).findById(id);
        Mockito.verify(mapper).map(bag1, BagDTO.class);

    }

    @Test
    void testFindByIdNotfound() {
        long id = 2L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> bagService.findById(id));
    }
}