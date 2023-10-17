/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafegoaereo.agents;
import java.util.List;

/**
 *
 * @author Natan
 */
public class Aeroporto {
    List<Aeronave> aeronaves;
    int quantidadePessoas = 0;
    String enderecoAeroporto = "";    
    boolean possuiAviaoNaPista = false;
    
    
    public Aeroporto(){}

    public Aeroporto(List<Aeronave> aeronaves, int quantidadePessoas, String enderecoAeroporto, boolean possuiAviaoNaPista)
    {
        //this.passageiros = passageiros;
        this.aeronaves = aeronaves;
        this.quantidadePessoas = quantidadePessoas;
        this.enderecoAeroporto = enderecoAeroporto;
        this.possuiAviaoNaPista = possuiAviaoNaPista;
    }
    
}
