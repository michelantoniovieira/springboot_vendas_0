package io.github.michelantoniovieira;

import io.github.michelantoniovieira.domain.entity.Cliente;
import io.github.michelantoniovieira.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication
{
    Cliente cliente = new Cliente();

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes)
    {
        return args ->
        {
            System.out.println("Salvando Clientes");
            clientes.salvar(new Cliente("Michel"));
            clientes.salvar(new Cliente("Outro Nome"));

            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Deletando Clientes");
            todosClientes = clientes.obterTodos();
            todosClientes.forEach(c ->
            {
                clientes.deletar(c);
            });

            todosClientes = clientes.obterTodos();
            if (todosClientes.isEmpty())
            {
                System.out.println("Nenhum cliente encontrado");
            } else
            {
                System.out.println(clientes.obterTodos());
            }

        };
    }

    public static void main(String[] args)
    {
        SpringApplication.run(VendasApplication.class, args);
    }
}
