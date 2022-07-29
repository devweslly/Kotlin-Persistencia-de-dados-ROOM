package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

private const val TAG = "DetalhesProduto"

class DetalhesProdutoActivity : AppCompatActivity() {

    // lateinit indica que podemos atribuir propeties que não precisa
    // ser inicializada no momento que estamos declarando ela (ela pode ser inicializada em outro momento)
    private lateinit var produto: Produto
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    // Método para sobrescrita de menu (menu de opções)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate para menus. Dentro de () vamos mandar o arq de resource
        // em seguida mandamos a referencia de menu que queremos add este inflate
        // que é a referencia que recebe via parametro no onCreateOptionsMenu
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Método para sobrescrita para configuração de Listener
    // Filtra qualquer tipo de menuque foi selecionado pela Activity
    // Se algum menu for acionado, este método é acionado também
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Precisamos garantir que ao acessar a propety produto ela terá o seu valor (está inicializada)
        // O if verifica se a variavel produto foi inicializada e evitando um nullable
        if (::produto.isInitialized) {
            // Acessando uma instacia do banco de dados
            val db = AppDatabase.instancia(this)
            val produtoDao = db.produtoDao() // tendo acesso ao produtoDao

            // Identificando qual menu foi selecionado (para depois fazer uma ação)
            // O parametro item que informa (filtra) qual menu foi selecionado. Assim configuramos listener para cada menu
            when (item.itemId) {
                // Filtrando opções que podem acontecer
                // Opção 1
                R.id.menu_detalhes_produto_remover -> {
                    // O que fazer (ação) ao selecionar opção 1
                    // chamando o comportamento do remove (que precisa de um produto)
                    produtoDao.remove(produto)
                    finish()
                }
                // Opção 1
                R.id.menu_detalhes_produto_editar -> {
                    // O que fazer (ação) ao selecionar opção 2
                    Log.i(TAG, "onOptionsItemSelected: editar")
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produto = produtoCarregado // tendo acesso a uma propetie de nome produto
            preencheCampos(produtoCarregado)
        } ?: finish()
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text =
                produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }

}