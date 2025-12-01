package org.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.dao.LivroDAO;
import org.model.Livro;
import org.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class LivrariaApp extends Application {

    private EntityManager em;
    private LivroDAO dao;
    private ObservableList<Livro> dadosTabela;

    // Variável para rastrear o livro que está sendo editado
    private Livro livroSelecionado = null; // CHAVE PARA A EDIÇÃO

    // Componentes da Tela
    private TextField txtTitulo = new TextField();
    private TextField txtAutor = new TextField();
    private TextField txtPreco = new TextField();
    private TableView<Livro> tabela = new TableView<>();
    private Button btnSalvar = new Button("Salvar");
    private Button btnDeletar = new Button("Deletar Selecionado");
    private Button btnLimpar = new Button("Novo (Limpar Campos)"); // Novo Botão para clareza

    @Override
    public void init() {
        // Inicializa conexão antes de abrir a janela
        em = JPAUtil.getEntityManager();
        dao = new LivroDAO(em);
        dadosTabela = FXCollections.observableArrayList();
    }

    @Override
    public void start(Stage stage) {
        // --- 1. Configuração dos Campos (Formulário) ---
        txtTitulo.setPromptText("Título do Livro");
        txtAutor.setPromptText("Autor");
        txtPreco.setPromptText("Preço (Ex: 50.00)");

        // --- 2. Configuração da Tabela ---
        TableColumn<Livro, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Livro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Livro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn<Livro, Double> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tabela.getColumns().addAll(colId, colTitulo, colAutor, colPreco);
        tabela.setItems(dadosTabela);
        atualizarTabela();

        // --- 3. Listener para EDIÇÃO (PREENCHE CAMPOS) ---
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Preenche os campos com os dados do livro selecionado
                txtTitulo.setText(newSelection.getTitulo());
                txtAutor.setText(newSelection.getAutor());
                // Garante que o preço seja formatado corretamente como String
                txtPreco.setText(String.valueOf(newSelection.getPreco()));

                // Marca o livro para ser editado ao clicar em 'Salvar'
                livroSelecionado = newSelection;
                btnSalvar.setText("Atualizar");
            } else {
                // Caso a seleção seja desfeita
                btnSalvar.setText("Salvar");
            }
        });

        // --- 4. Ações dos Botões ---

        // 4.1. Botão Salvar (Criação OU Atualização)
        btnSalvar.setOnAction(e -> salvarOuAtualizarLivro());

        // 4.2. Botão Deletar
        btnDeletar.setOnAction(e -> {
            Livro selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                dao.remover(selecionado.getId());
                limparCampos();
                atualizarTabela();
            } else {
                mostrarAlerta("Atenção", "Selecione um livro para deletar.");
            }
        });

        // 4.3. Botão Limpar
        btnLimpar.setOnAction(e -> limparCampos());


        // --- 5. Layout e Scene ---
        HBox boxBotoesForm = new HBox(10, btnSalvar, btnLimpar);
        HBox boxCampos = new HBox(10, txtTitulo, txtAutor, txtPreco);

        VBox root = new VBox(15, boxCampos, boxBotoesForm, new Separator(), tabela, btnDeletar);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 650, 450);
        stage.setTitle("Gerenciador de Livros (JavaFX + JPA + Postgres)");
        stage.setScene(scene);
        stage.show();
    }

    // --- NOVO MÉTODO CORE: Lógica Salvar/Atualizar ---
    private void salvarOuAtualizarLivro() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            // Tenta converter o preço, pode lançar NumberFormatException
            Double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));

            Livro livro;

            // 1. É EDIÇÃO?
            if (livroSelecionado != null) {
                livro = livroSelecionado;
                livro.setTitulo(titulo);
                livro.setAutor(autor);
                livro.setPreco(preco);
                // O ID já está definido, o DAO fará o UPDATE
            } else {
                // 2. É CRIAÇÃO?
                livro = new Livro(titulo, autor, preco);
                // O ID será gerado pelo banco, o DAO fará o INSERT
            }

            // Persiste no banco de dados
            dao.salvar(livro);

            limparCampos();
            atualizarTabela();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Preço", "Por favor, insira um valor numérico válido para o preço (Ex: 50.00).");
        } catch (Exception ex) {
            mostrarAlerta("Erro de Persistência", "Ocorreu um erro ao salvar/atualizar o livro: " + ex.getMessage());
        }
    }


    @Override
    public void stop() {
        if (em != null && em.isOpen()) em.close();
    }

    // Métodos Auxiliares
    private void atualizarTabela() {
        dadosTabela.clear();
        dadosTabela.addAll(dao.listarTodos());
    }

    private void limparCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtPreco.clear();
        tabela.getSelectionModel().clearSelection(); // Limpa a seleção da tabela
        livroSelecionado = null; // Desfaz a marcação de edição
        btnSalvar.setText("Salvar"); // Volta o botão para 'Salvar'
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}