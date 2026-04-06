package br.com.hospitalcrm;

import br.com.hospitalcrm.controller.AgendamentoController;
import br.com.hospitalcrm.controller.LeadController;
import br.com.hospitalcrm.controller.MedicoController;
import br.com.hospitalcrm.controller.PacienteController;
import br.com.hospitalcrm.model.Agendamento;
import br.com.hospitalcrm.model.Lead;
import br.com.hospitalcrm.model.Medico;
import br.com.hospitalcrm.model.Paciente;
import br.com.hospitalcrm.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Scene scene = new Scene(new MainView().getLayout(), 600, 500);
            stage.setTitle("Hospital CRM");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== ZONA DE TESTES — Hospital CRM ===\n");

        System.out.println("-- TESTE 1: Cadastrar Paciente --");
        try {
            PacienteController pacienteController = new PacienteController();
            Paciente p = new Paciente();
            p.setNome("Carlos Eduardo Silva");
            p.setCpf("123.456.789-01");
            p.setEmail("carlos.silva@email.com");
            p.setTelefone("(11) 91234-5678");
            p.setSexo("M");
            p.setPeso(80.0);
            p.setAltura(1.75);
            p.setDataNascimento(new Date(90, 4, 15));
            p.setPorOndeConheceu("Instagram");
            System.out.println(pacienteController.salvar(p));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\n-- TESTE 2: CPF duplicado (CadastroDuplicadoException) --");
        try {
            PacienteController pacienteController = new PacienteController();
            Paciente p2 = new Paciente();
            p2.setNome("Outro Nome");
            p2.setCpf("123.456.789-01");
            p2.setPeso(70.0);
            p2.setAltura(1.70);
            System.out.println(pacienteController.salvar(p2));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\n-- TESTE 3: Cadastrar Médico --");
        try {
            MedicoController medicoController = new MedicoController();
            Medico m = new Medico();
            m.setNome("Dra. Ana Paula Souza");
            m.setCrm("CRM-SP-123456");
            m.setTelefone("(11) 98765-4321");
            m.setEmail("ana.souza@hospitalcrm.com");
            System.out.println(medicoController.salvar(m));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\n-- TESTE 4: Cadastrar Lead --");
        try {
            LeadController leadController = new LeadController();
            Lead l = new Lead();
            l.setNome("Fernanda Oliveira");
            l.setTelefone("(11) 94444-5555");
            l.setEmail("fernanda.oliveira@gmail.com");
            l.setInteresse("Consulta Cardiológica");
            l.setOrigem("Google");
            l.setStatus("QUENTE");
            System.out.println(leadController.salvar(l));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\n-- TESTE 5: Agendamento com data no passado --");
        try {
            AgendamentoController agendamentoController = new AgendamentoController();
            Agendamento a = new Agendamento();
            a.setPacienteId(1L);
            a.setMedicoId(1L);
            a.setDataHora(LocalDateTime.now().minusDays(1));
            a.setStatus("AGENDADO");
            agendamentoController.salvar(a);
        } catch (Exception e) {
            System.out.println("Erro (esperado): " + e.getMessage());
        }

        System.out.println("\n=== FIM DA ZONA DE TESTES ===\n");

        Application.launch(Main.class, args);
    }
}