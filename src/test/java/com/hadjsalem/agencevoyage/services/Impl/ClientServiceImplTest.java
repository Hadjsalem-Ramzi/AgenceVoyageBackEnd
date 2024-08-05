package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.entities.Client;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.ClientMapper;
import com.hadjsalem.agencevoyage.repositories.ClientRepository;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {


    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl underTest;

  @Mock
  private ObjectsValidators<Client> clientValidators;




    @Test
    void shouldSaveNewClient(){
        ClientDto clientDto=ClientDto.builder().firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();
        Client client=Client.builder().firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();
        Client savedClient= Client.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();
        ClientDto expectedClient= ClientDto.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();

        when(clientMapper.fromClientDto(clientDto)).thenReturn(client);
        when(clientRepository.existsByEmail(clientDto.getEmail())).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(savedClient);
        when(clientMapper.fromClient(savedClient)).thenReturn(expectedClient);
        Mockito.doNothing().when(clientValidators).validate(Mockito.any(Client.class));
        ClientDto result = underTest.saveClient(clientDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedClient);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenClientExists() {
        ClientDto clientDto = ClientDto.builder()
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .email("hadjsalemramzi@gmail.com")
                .password("123546")
                .build();

        Client client = Client.builder()
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .email("hadjsalemramzi@gmail.com")
                .password("123546")
                .build();

        when(clientMapper.fromClientDto(clientDto)).thenReturn(client);
        when(clientRepository.existsByEmail(clientDto.getEmail())).thenReturn(true);
        Mockito.doNothing().when(clientValidators).validate(Mockito.any(Client.class));

        assertThatThrownBy(() -> underTest.saveClient(clientDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un client est existe avec cette email");

        verify(clientRepository, times(1)).existsByEmail(clientDto.getEmail());
        verify(clientRepository, never()).save(any(Client.class));
    }
    @Test
    void ShouldGetAllClients() {
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(2L);
        List<Client> Clients = Arrays.asList(client1, client2);
        Page<Client> pageClients = new PageImpl<>(Clients);

        ClientDto clientDto1 = new ClientDto();
        clientDto1.setId(1L);
        ClientDto clientDto2 = new ClientDto();
        clientDto2.setId(2L);

        when(clientRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageClients);
        when(clientMapper.fromClient(client1)).thenReturn(clientDto1);
        when(clientMapper.fromClient(client2)).thenReturn(clientDto2);

        // When
        PageResponse<ClientDto> result = underTest.getClients(0, 2);

        // Then
        assertEquals(2, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getSize());
        assertEquals(true, result.isFirst());
        assertEquals(true, result.isLast());
    }
/******         Test GetById Method                ***/
    @Test
    void ShouldFindClientById() {
        Long givenClientId = 1L;
        Client client = Client.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi24@gmail.com").password("1234568").build();
        ClientDto expected = ClientDto.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi24@gmail.com").password("1234568").build();
        when(clientRepository.findById(givenClientId)).thenReturn(Optional.of(client));
        when(clientMapper.fromClient(client)).thenReturn(expected);
        ClientDto result = underTest.findClientById(givenClientId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindClientById() {
        Long givenClientById = 5l;
        when(clientRepository.findById(givenClientById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findClientById(givenClientById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Client Not Found");

    }

    /***                Test Method FindClientByNumTel                    ************/
    @Test
    void ShouldfindClientByEmail() {
        String givenEmail = "hadjsalemramzi24@gmail.com";
        Client client = Client.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi24@gmail.com").password("1254689").build();
        ClientDto expected = ClientDto.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi24@gmail.com").password("1254689").build();

        when(clientRepository.findClientByEmail(givenEmail)).thenReturn(Optional.of(client));
        when(clientMapper.fromClient(client)).thenReturn(expected);

        ClientDto result = underTest.findClientByEmail(givenEmail);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindClientByEmail() {
       String  givenEmail= "hadjsalemramzi24@gmail.com";

        when(clientRepository.findClientByEmail(givenEmail)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findClientByEmail(givenEmail)).isInstanceOf(EntityNotFoundException.class);
    }

/**********************************************************************************************/
    /***    Test  Method updateClient ******/

    @Test
    void ShouldUpdateClient() {
        Long givenClientId = 2L;
        Client client = Client.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();
        ClientDto clientDto = ClientDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();
        Client updatedClient = Client.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();
        ClientDto expected = ClientDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();

        when(clientRepository.findById(givenClientId)).thenReturn(Optional.of(client));
        when(clientRepository.saveAndFlush(client)).thenReturn(updatedClient);
        when(clientMapper.fromClient(updatedClient)).thenReturn(clientDto);

        ClientDto result = underTest.updateClient(clientDto, givenClientId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateClient() {
        Long givenClientId = 2L;
        Client client = Client.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();
        ClientDto clientDto = ClientDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();
        Client updatedClient = Client.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();
        ClientDto expected = ClientDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("54604022").build();

        when(clientRepository.findById(givenClientId)).thenReturn(Optional.of(client));
        when(clientRepository.saveAndFlush(client)).thenReturn(updatedClient);
        when(clientMapper.fromClient(updatedClient)).thenReturn(clientDto);

        ClientDto result = underTest.updateClient(clientDto, givenClientId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenClientNotFound() {
        Long givenClientId = 2L;
        ClientDto clientDto = ClientDto.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .password("54604022")
                .email("hadjsalemramzi@gamil.com")
                .build();

        when(clientRepository.findById(givenClientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateClient(clientDto, givenClientId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenClientId = 2L;
        Client client = Client.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .password("54604022")
                .email("hadjsalemramzi@gamil.com")
                .build();
        ClientDto clientDto = ClientDto.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .password("54604022")
                .email("hadjsalemramzi@gamil.com")
                .build();

        when(clientRepository.findById(givenClientId)).thenReturn(Optional.of(client));
        when(clientRepository.saveAndFlush(client)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateClient(clientDto, givenClientId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenClientDtoIsInvalid() {
        Long givenClientId = 2L;
        Client client = Client.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .password("54604022")
                .email("hadjsalemramzi@gamil.com")
                .build();
        ClientDto invalidClientDto = ClientDto.builder()
                .id(2L)
                .firstName("") // Invalid data
                .lastName("Hadjsalem")
                .password("54604022")
                .email("hadjsalemramzi@gamil.com")
                .build();

        when(clientRepository.findById(givenClientId)).thenReturn(Optional.of(client));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(clientValidators).validate(any(Client.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateClient(invalidClientDto, givenClientId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /****             Test Méthod Delete       **********/

    @Test
    void shouldDeleteClientById() {
        Long clientId = 1L;
       Client client = Client.builder().id(1L).firstName("Ramzi").lastName("hadj").email("ramzi@gmail.com").password("123456789").build();
       ClientDto clientDto = ClientDto.builder().id(1L).firstName("Ramzi").lastName("hadj").email("ramzi@gmail.com").password("123456789").build();
        when(clientRepository.findById(clientDto.getId())).thenReturn(Optional.of(client));
         underTest.deleteClient(clientId);

   verify(clientRepository,times(1)).findById(clientId);
   verify(clientRepository,times(1)).deleteById(clientId);

    }

    @Test
    void shouldNotDeleteClientIfNotFound() {
        Long ClientId = 1L;

        // Simuler que le Client n'existe pas en faisant en sorte que l'Optional soit vide
        when(clientRepository.findById(ClientId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteClient(ClientId)).isInstanceOf(EntityNotFoundException.class).hasMessage("client with this id Not Found");
    }

}
















