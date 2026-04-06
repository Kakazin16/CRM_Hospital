package br.com.hospitalcrm.view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainView {

    private TabPane tabPane = new TabPane();

    public MainView() {

        Tab pacienteTab = new Tab("Paciente", new PacienteView().getLayout());
        Tab medicoTab = new Tab("Médico", new MedicoView().getLayout());
        Tab leadTab = new Tab("Lead", new LeadView().getLayout());
        Tab agendamentoTab = new Tab("Agendamento", new AgendamentoView().getLayout());

        pacienteTab.setClosable(false);
        medicoTab.setClosable(false);
        leadTab.setClosable(false);
        agendamentoTab.setClosable(false);

        tabPane.getTabs().addAll(pacienteTab, medicoTab, leadTab, agendamentoTab);
    }

    public TabPane getLayout() {
        return tabPane;
    }
}