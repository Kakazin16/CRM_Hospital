package br.com.hospitalcrm.view;

import br.com.hospitalcrm.controller.LeadController;
import br.com.hospitalcrm.model.Lead;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LeadView {

    private VBox layout = new VBox(10);

    private TextField nome = new TextField();
    private TextField telefone = new TextField();
    private TextField email = new TextField();
    private TextField interesse = new TextField();
    private TextField origem = new TextField();

    private ComboBox<String> status = new ComboBox<>();

    private Button salvar = new Button("Salvar");

    private Label resultado = new Label();

    private LeadController controller = new LeadController();

    public LeadView() {

        layout.setPadding(new Insets(20));

        nome.setPromptText("Nome");
        telefone.setPromptText("Telefone");
        email.setPromptText("Email");
        interesse.setPromptText("Interesse");
        origem.setPromptText("Origem");

        status.getItems().addAll("FRIO", "MORNO", "QUENTE");
        status.setPromptText("Status");

        salvar.setOnAction(e -> salvarLead());

        layout.getChildren().addAll(
                nome, telefone, email,
                interesse, origem, status,
                salvar, resultado
        );
    }

    private void salvarLead() {
        try {
            Lead l = new Lead();

            l.setNome(nome.getText());
            l.setTelefone(telefone.getText());
            l.setEmail(email.getText());
            l.setInteresse(interesse.getText());
            l.setOrigem(origem.getText());
            l.setStatus(status.getValue());

            controller.salvar(l);

            resultado.setText("Lead salvo!");

        } catch (Exception e) {
            e.printStackTrace();
            resultado.setText("Erro: " + e.getMessage());
        }
    }

    public VBox getLayout() {
        return layout;
    }
}