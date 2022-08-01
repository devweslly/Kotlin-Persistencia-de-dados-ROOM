package br.com.alura.orgs.ui.activity

import android.content.Intent
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

    private var produtoId: Long? = null

    // lateinit indica que podemos atribuir propeties que não precisa
    // ser inicializada no momento que estamos declarando ela (ela pode ser inicializada em outro momento)
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    // Criando uma instacia do Dao
    private val produtoDao by lazy {
        // Acessando uma instacia do banco de dados
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        // Pegar o ID e carregar um produto
        produtoId?.let { id ->
            // Inicializando este produto
            produto = produtoDao.buscaPorId(id)
        }
        // Verifica se o produto é nulo. Se for nulo, não existir no banco de dados, finaliza a activity
        produto?.let {
            // Se tiver as infos esperadas, preenche as infos e manda o produto
            preencheCampos(it)
        } ?: finish()
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

        // Identificando qual menu foi selecionado (para depois fazer uma ação)
        // O parametro item que informa (filtra) qual menu foi selecionado. Assim configuramos listener para cada menu
        when (item.itemId) {
            // Filtrando opções que podem acontecer
            // Opção 1
            R.id.menu_detalhes_produto_remover -> {
                // O que fazer (ação) ao selecionar opção 1
                // chamando o comportamento do remove (que precisa de um produto)
                produto?.let { produtoDao.remove(it) }
                finish()
            }
            // Opção 2
            R.id.menu_detalhes_produto_editar -> {
                // O que fazer (ação) ao selecionar opção 2
                // abrir a activity por meio da intent
                // Criando uma nova Intent que vai receber o contexto da nossa classe DetalhesProdutoActivity (this)
                // e em seguida acessamos a FormularioProdutoActivity
                // A scope function apply indica que queremos colocar mais
                // comportamentos exclusivos dentro do código (dentro da Intent)
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(
                        CHAVE_PRODUTO,
                        produto
                    ) // coloca uma chave e um valor e manda info para a activity
                    startActivity(this)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        // O getParcelableExtra que a partir dele conseguimos chamar novas Activities e mandar infos para elas
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produtoId = produtoCarregado.id
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