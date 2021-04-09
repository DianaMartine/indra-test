package dianaMartine.diana.carros.bean;

import dianaMartine.diana.carros.dao.CrudDAO;
import dianaMartine.diana.carros.util.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class CrudBean<E, D extends CrudDAO> {

    private String estadoTela = "buscar";

    private E entidade;
    private List<E> entidades;

    public void novo() {
        entidade = criarNovaEntidade();
        mudarParaInseri();
    }

    public void salvar() {
        try {
            getDAO().salvar(entidade);
            entidade = criarNovaEntidade();
            addMsg("Salvo com sucesso", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            addMsg(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar() {
        this.entidade = entidade;
        mudarParaEdita();
    }

    public void deletar() {
        try {
            getDAO().deletar(entidade);
            entidades.remove(entidade);
            addMsg("Deletado com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            addMsg(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void buscar() {
        if (isBusca() == false) {
            mudarParaBusca();
            return;
        }
        try {
            entidades = getDAO().buscar();
            if (entidades == null || entidades.size() < 1) {
                addMsg("Nenhum dado encontrado", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            addMsg(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void addMsg(String msg, FacesMessage.Severity typeError) {
        FacesMessage fm = new FacesMessage(typeError, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }

    public abstract D getDAO();

    public abstract E criarNovaEntidade();

    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }

    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }

    public boolean isBusca() {
        return "buscar".equals(estadoTela);
    }

    public void mudarParaInseri() {
        estadoTela = "inserir";
    }

    public void mudarParaEdita() {
        estadoTela = "editar";
    }

    public void mudarParaBusca() {
        estadoTela = "buscar";
    }
}