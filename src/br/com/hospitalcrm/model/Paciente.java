package br.com.hospitalcrm.model;

import java.util.Date;

public class Paciente {
    private Long id;
    private String nome, cpf, email, telefone, porOndeConheceu, sexo;
    private java.util.Date dataNascimento;
    private double peso, altura, imc;
    private boolean ativo;


    public double calcularIMC(){
        if (altura <= 0) throw new IllegalArgumentException("Altura inválida");
        this.imc = peso / (altura * altura);
        return this.imc;
    }

    public Paciente(){}

    public Paciente(Long id, String nome, String cpf, String email, String telefone, String porOndeConheceu, String sexo, Date dataNascimento, double peso, double altura, double imc) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.porOndeConheceu = porOndeConheceu;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPorOndeConheceu() {
        return porOndeConheceu;
    }

    public void setPorOndeConheceu(String porOndeConheceu) {
        this.porOndeConheceu = porOndeConheceu;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
