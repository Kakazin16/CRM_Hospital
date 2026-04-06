package br.com.hospitalcrm.view;

import br.com.hospitalcrm.controller.AgendamentoController;
import br.com.hospitalcrm.model.Agendamento;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;

public class AgendamentoView {

    private VBox layout = new VBox(10);

    private TextField pacienteId = new TextField();
    private TextField medicoId = new TextField();
    private TextField dataHora = new TextField();

    private ComboBox<String> status = new ComboBox<>();

    private Button salvar = new Button("Salvar");

    private Label resultado = new Label();

    private AgendamentoController controller = new AgendamentoController();

    public AgendamentoView() {

        layout.setPadding(new Insets(20));

        pacienteId.setPromptText("ID Paciente");
        medicoId.setPromptText("ID Médico");
        dataHora.setPromptText("DataHora (yyyy-MM-ddTHH:mm)");

        status.getItems().addAll(
                "AGENDADO",
                "CANCELADO",
                "REALIZADO",
                "ATENDIDO",
                "FALTA",
                "ABANDONO",
                "REAGENDADO"
        );
        status.setPromptText("Status");

        salvar.setOnAction(e -> salvarAgendamento());

        layout.getChildren().addAll(
                pacienteId, medicoId, dataHora, status,
                salvar, resultado
        );
    }

    private void salvarAgendamento() {
        try {
            Agendamento a = new Agendamento();
            a.setPacienteId(Long.parseLong(pacienteId.getText()));
            a.setMedicoId(Long.parseLong(medicoId.getText()));
            a.setDataHora(LocalDateTime.parse(dataHora.getText()));
            a.setStatus(status.getValue());

            controller.salvar(a);
            resultado.setText("Agendamento salvo!");

        } catch (Exception e) {
            e.printStackTrace();
            resultado.setText("Erro: " + e.getMessage());
        }
    }

    public VBox getLayout() {
        return layout;
    }
}