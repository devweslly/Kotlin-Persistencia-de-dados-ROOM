package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

private const val TAG = "DetalhesProduto"

class DetalhesProdutoActivity : AppCompatActivity() {

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
        // Identificando qual menu foi selecionado (para depois fazer uma ação)
        // O parametro item que informa (filtra) qual menu foi selecionado. Assim configuramos listener para cada menu
        when(item.itemId){
            // Filtrando opções que podem acontecer
            // Opção 1
            R.id.menu_detalhes_produto_remover -> {
                // O que fazer (ação) ao selecionar opção 1
                Log.i(TAG, "onOptionsItemSelected: remover")
            }
            // Opção 1
            R.id.menu_detalhes_produto_editar -> {
                // O que fazer (ação) ao selecionar opção 2
                Log.i(TAG, "onOptionsItemSelected: editar")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
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