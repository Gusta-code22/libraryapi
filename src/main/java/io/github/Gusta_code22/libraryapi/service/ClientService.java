package io.github.Gusta_code22.libraryapi.service;

import io.github.Gusta_code22.libraryapi.model.Client;
import io.github.Gusta_code22.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);

    }

    public Client obterPorClientid(String clientId){
        return repository.findByClientId(clientId);
    }
}
