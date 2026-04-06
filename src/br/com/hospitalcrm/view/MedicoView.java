package br.com.hospitalcrm.view;

import br.com.hospitalcrm.controller.MedicoController;
import br.com.hospitalcrm.model.Medico;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class MedicoView {

    private VBox layout = new VBox(10);

    private TextField nome = new TextField();
    private TextField crm = new TextField();
    private TextField telefone = new TextField();
    private TextField email = new TextField();
    private TextField procedimentos = new TextField();

    private Button salvar = new Button("Salvar");
    private Button editar = new Button("Salvar Edição");
    private Button listar = new Button("Listar Médicos");
    private Button inativar = new Button("Inativar Selecionado");

    private Label resultado = new Label();

    private ListView<String> listaMedicos = new ListView<>();
    private List<Medico> medicosCarregados;
    private Medico medicoSelecionado = null;

    private MedicoController controller = new MedicoController();

    public MedicoView() {
        layout.setPadding(new Insets(20));

        nome.setPromptText("Nome");
        crm.setPromptText("CRM");
        telefone.setPromptText("Telefone");
        email.setPromptText("Email");
        procedimentos.setPromptText("Procedimentos atendidos");

        listaMedicos.setPrefHeight(150);
        listaMedicos.setVisible(false);
        listaMedicos.setManaged(false);

        inativar.setVisible(false);
        inativar.setManaged(false);

        editar.setVisible(false);
        editar.setManaged(false);

        // Ao selecionar na lista, preenche os campos
        listaMedicos.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            if (index >= 0 && medicosCarregados != null && index < medicosCarregados.size()) {
                medicoSelecionado = medicosCarregados.get(index);
                preencherCampos(medicoSelecionado);
                editar.setVisible(true);
                editar.setManaged(true);
            }
        });

        salvar.setOnAction(e -> salvarMedico());
        editar.setOnAction(e -> editarMedico());
        listar.setOnAction(e -> listarMedicos());
        inativar.setOnAction(e -> inativarMedico());

        layout.getChildren().addAll(
                nome, crm, telefone, email, procedimentos,
                salvar, editar, resultado,
                listar, listaMedicos, inativar
        );
    }

    private void preencherCampos(Medico m) {
        nome.setText(m.getNome() != null ? m.getNome() : "");
        crm.setText(m.getCrm() != null ? m.getCrm() : "");
        telefone.setText(m.getTelefone() != null ? m.getTelefone() : "");
        email.setText(m.getEmail() != null ? m.getEmail() : "");
    }

    private void limparCampos() {
        nome.clear(); crm.clear(); telefone.clear(); email.clear(); procedimentos.clear();
        medicoSelecionado = null;
        editar.setVisible(false);
        editar.setManaged(false);
    }

    private void salvarMedico() {
        try {
            Medico m = new Medico();
            m.setNome(nome.getText());
            m.setCrm(crm.getText());
            m.setTelefone(telefone.getText());
            m.setEmail(email.getText());

            String msg = controller.salvar(m);
            resultado.setText(msg);
            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
            resultado.setText("Erro: " + e.getMessage());
        }
    }

    private void editarMedico() {
        try {
            if (medicoSelecionado == null) {
                resultado.setText("Selecione um médico para editar.");
                return;
            }

            medicoSelecionado.setNome(nome.getText());
            medicoSelecionado.setCrm(crm.getText());
            medicoSelecionado.setTelefone(telefone.getText());
            medicoSelecionado.setEmail(email.getText());

            String msg = controller.editar(medicoSelecionado);
            resultado.setText(msg);
            limparCampos();
            listarMedicos();

        } catch (Exception e) {
            e.printStackTrace();
            resultado.setText("Erro: " + e.getMessage());
        }
    }

    private void listarMedicos() {
        try {
            listaMedicos.getItems().clear();
            medicosCarregados = controller.listar();

            if (medicosCarregados.isEmpty()) {
                listaMedicos.getItems().add("Nenhum médico cadastrado.");
                inativar.setVisible(false);
                inativar.setManaged(false);
            } else {
                for (Medico m : medicosCarregados) {
                    String linha = m.getNome()
                            + " | CRM: " + m.getCrm()
                            + " | Tel: " + m.getTelefone()
                            + " | Email: " + m.getEmail();
                    listaMedicos.getItems().add(linha);
                }
                inativar.setVisible(true);
                inativar.setManaged(true);
            }

            listaMedicos.setVisible(true);
            listaMedicos.setManaged(true);

        } catch (Exception e) {
            resultado.setText("Erro ao listar: " + e.getMessage());
        }
    }

    private void inativarMedico() {
        int index = listaMedicos.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            resultado.setText("Selecione um médico para inativar.");
            return;
        }
        Medico selecionado = medicosCarregados.get(index);
        String msg = controller.inativar(selecionado.getId());
        resultado.setText(msg);
        limparCampos();
        listarMedicos();
    }

    public VBox getLayout() {
        return layout;
    }
}