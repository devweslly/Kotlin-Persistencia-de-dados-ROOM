package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.alura.orgs.dao.ProdutosDao
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

        // Criar banco de dados
        // db vai dar acesso ao Dao e aos comportamentes de buscar e salvar
        val db = Room.databaseBuilder( // Método responsável por criar a instancia do banco de dados
            // Contexto da Activity
            this,
            // Classe de referência que representa o Database
            AppDatabase::class.java,
            // String que representa o nome do arquivo que será gerado
            // para conter a implementação do banco de dado
            // vamos usar o nome do app simulando uma extensão de banco de dados (facilidade em identificar)
            "orgs.db"
        ).allowMainThreadQueries() // Config o AppDatabase para rodar na thread principal (Não é uma boa prática)
            .build() // gerar uma instância do tipo da classe AppDatabase mandada como referencia
        val produtoDao = db.produtoDao() // Acessando o Dao
        // Chamando comportamento
        produtoDao.salva(
            Produto(
                nome = "teste nome 1",
                descricao = "teste desc 1",
                valor = BigDecimal("10.0")
            )
        )
        // Atualiza lista de produtos e busca todos os produtos no banco de dados
        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onResume() {
        super.onResume()
        //adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }
    }
}