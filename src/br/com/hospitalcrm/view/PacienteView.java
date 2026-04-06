package br.com.hospitalcrm.view;

import br.com.hospitalcrm.controller.PacienteController;
import br.com.hospitalcrm.model.Paciente;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PacienteView {

    private VBox layout = new VBox(10);

    private TextField nome = new TextField();
    private TextField cpf = new TextField();
    private TextField email = new TextField();
    private TextField telefone = new TextField();
    private TextField peso = new TextField();
    private TextField altura = new TextField();
    private TextField dataNascimento = new TextField();
    private TextField sexo = new TextField();
    private TextField origem = new TextField();

    private Button salvar = new Button("Salvar");
    private Button editar = new Button("Salvar Edição");
    private Button listar = new Button("Listar Pacientes");
    private Button inativar = new Button("Inativar Selecionado");

    private Label resultado = new Label();

    private ListView<String> listaPacientes = new ListView<>();
    private List<Paciente> pacientesCarregados;
    private Paciente pacienteSelecionado = null;

    private PacienteController controller = new PacienteController();

    public PacienteView() {
        layout.setPadding(new Insets(20));

        nome.setPromptText("Nome");
        cpf.setPromptText("CPF");
        email.setPromptText("Email");
        telefone.setPromptText("Telefone");
        peso.setPromptText("Peso (kg)");
        altura.setPromptText("Altura (ex: 1.75)");
        dataNascimento.setPromptText("Data Nascimento (dd/MM/yyyy)");
        sexo.setPromptText("Sexo (M/F)");
        origem.setPromptText("Como conheceu");

        listaPacientes.setPrefHeight(150);
        listaPacientes.setVisible(false);
        listaPacientes.setManaged(false);

        inativar.setVisible(false);
        inativar.setManaged(false);

        editar.setVisible(false);
        editar.setManaged(false);

        // Ao selecionar na lista, preenche os campos
        listaPacientes.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            if (index >= 0 && pacientesCarregados != null && index < pacientesCarregados.size()) {
                pacienteSelecionado = pacientesCarregados.get(index);
                preencherCampos(pacienteSelecionado);
                editar.setVisible(true);
                editar.setManaged(true);
            }
        });

        salvar.setOnAction(e -> salvarPaciente());
        editar.setOnAction(e -> editarPaciente());
        listar.setOnAction(e -> listarPacientes());
        inativar.setOnAction(e -> inativarPaciente());

        layout.getChildren().addAll(
                nome, cpf, email, telefone,
                peso, altura, dataNascimento, sexo, origem,
                salvar, editar, resultado,
                listar, listaPacientes, inativar
        );
    }

    private void preencherCampos(Paciente p) {
        nome.setText(p.getNome() != null ? p.getNome() : "");
        cpf.setText(p.getCpf() != null ? p.getCpf() : "");
        email.setText(p.getEmail() != null ? p.getEmail() : "");
        telefone.setText(p.getTelefone() != null ? p.getTelefone() : "");
        peso.setText(String.valueOf(p.getPeso()));
        altura.setText(String.valueOf(p.getAltura()));
        sexo.setText(p.getSexo() != null ? p.getSexo() : "");
        origem.setText(p.getPorOndeConheceu() != null ? p.getPorOndeConheceu() : "");
        if (p.getDataNascimento() != null) {
            dataNascimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(p.getDataNascimento()));
        } else {
            dataNascimento.setText("");
        }
    }

    private void limparCampos() {
        nome.clear(); cpf.clear(); email.clear(); telefone.clear();
        peso.clear(); altura.clear(); dataNascimento.clear();
        sexo.clear(); origem.clear();
        pacienteSelecionado = null;
        editar.setVisible(false);
        editar.setManaged(false);
    }

    private void salvarPaciente() {
        try {
            if (nome.getText().isEmpty() || cpf.getText().isEmpty()) {
                resultado.setText("Nome e CPF são obrigatórios");
                return;
            }

            Paciente p = new Paciente();
            p.setNome(nome.getText());
            p.setCpf(cpf.getText());
            p.setEmail(email.getText());
            p.setTelefone(telefone.getText());
            p.setSexo(sexo.getText());
            p.setPorOndeConheceu(origem.getText());

            double pesoValue = Double.parseDouble(peso.getText().replace(",", "."));
            double alturaValue = Double.parseDouble(altura.getText().replace(",", "."));
            if (alturaValue > 10) alturaValue = alturaValue / 100;
            p.setPeso(pesoValue);
            p.setAltura(alturaValue);

            if (!dataNascimento.getText().isEmpty()) {
                Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento.getText());
                p.setDataNascimento(data);
            }

            String msg = controller.salvar(p);
            resultado.setText(msg);
            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
            resultado.setText("Erro: " + e.getMessage());
        }
    }

    private void editarPaciente() {
        try {
            if (pacienteSelecionado == null) {
                resultado.setText("Selecione um paciente para editar.");
                return;
            }

            pacienteSelecionado.setNome(nome.getText());
            pacienteSelecionado.setCpf(cpf.getText());
            pacienteSelecionado.setEmail(email.getText());
            pacienteSelecionado.setTelefone(telefone.getText());
            pacienteSelecionado.setSexo(sexo.getText());
            pacienteSelecionado.setPorOndeConheceu(origem.getText());

            double pesoValue = Double.parseDouble(peso.getText().replace(",", "."));
            double alturaValue = Double.parseDouble(altura.getText().replace(",", "."));
            if (alturaValue > 10) alturaValue = alturaValue / 100;
            pacienteSelecionado.setPeso(pesoValue);
            pacienteSelecionado.setAltura(alturaValue);

            if (!dataNascimento.getText().isEmpty()) {
                Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento.getText());
                pacienteSelecionado.setDataNascimento(data);
            }

            String msg = controller.editar(pacienteSelecionado);
            resultado.setText(msg);
            limparCampos();
            listarPacientes();

        } catch (Exception e) {
            e.printStackTrace();
            resultado.setText("Erro: " + e.getMessage());
        }
    }

    private void listarPacientes() {
        try {
            listaPacientes.getItems().clear();
            pacientesCarregados = controller.listar();

            if (pacientesCarregados.isEmpty()) {
                listaPacientes.getItems().add("Nenhum paciente cadastrado.");
                inativar.setVisible(false);
                inativar.setManaged(false);
            } else {
                for (Paciente p : pacientesCarregados) {
                    String linha = p.getNome() + " | CPF: " + p.getCpf()
                            + " | IMC: " + String.format("%.2f", p.getImc())
                            + " | Tel: " + p.getTelefone();
                    listaPacientes.getItems().add(linha);
                }
                inativar.setVisible(true);
                inativar.setManaged(true);
            }

            listaPacientes.setVisible(true);
            listaPacientes.setManaged(true);

        } catch (Exception e) {
            resultado.setText("Erro ao listar: " + e.getMessage());
        }
    }

    private void inativarPaciente() {
        int index = listaPacientes.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            resultado.setText("Selecione um paciente para inativar.");
            return;
        }
        Paciente selecionado = pacientesCarregados.get(index);
        String msg = controller.inativar(selecionado.getId());
        resultado.setText(msg);
        limparCampos();
        listarPacientes();
    }

    public VBox getLayout() {
        return layout;
    }
}