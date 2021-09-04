import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.net.Socket;

//*****************************************************************************************************************
//                                                                                                                *
// Autor: Antonio Bernardo de Vasconcellos Praxedes                                                               *
//                                                                                                                * 
// Data: 03/09/2021                                                                                               *
//                                                                                                                *
// Classe: Mensagem                                                                                               *
//                                                                                                                *
// Atributos: variáveis de supervisão e controle                                                                  *
//                                                                                                                *
// Métodos: montagem de mensagens em formato XML para enviar ao navegador                                         *
//                                                                                                                *
//*****************************************************************************************************************
//
public class Mensagem {

	private String ComRecHTTP;
	private String MsgXML;

	// Estados Gerais
	private int Est24Vcc;
	private int EstRede;
	private int EstCom1;
	private int MdOp;
	private int MdCom;
	private int MdCtrl1;
	private int MdCtrl;
	private int Carga1;
	private int Carga2;
	private int Carga3;
	private int Carga4;
	private int HabCom;
	private int EstadoInversor1;
	private int EstadoInversor2;
	private int EstadoCarga3;
	private  int CT2Inv;
	private  int CT1Inv;
	private  int CT3Inv;
	private  int UTR1Com;
	private  int UTR2Com;
	private  int CDBat;
	private  int FonteCC1Ligada;
	private  int FonteCC2Ligada;
	
	// Medidas Gerais
	private  int Icarga3;       // Corrente Carga 3 (Geladeira)
	private  int VRede;         // Tensão da Rede
	private  int VBat;          // Tensão do Banco de Baterias
	private  int VMBat;         // Tensão Média Estendida do Banco de Baterias
	private  int ICircCC;       // Corrente Total dos Circuitos CC
	private  int WCircCC;       // Potência Total dos Circuitos CC
	private  int ITotCg;        // Corrente Total Consumida pelas Cargas
	private  int WTotCg;        // Potência Total Consumida pelas Cargas
	private  int IFonteCC;      // Corrente de Saída da Fonte CC
	private  int WFonteCC;      // Potência de Saída da Fonte CC
	private  int IBat;          // Corrente de Carga / Descarga do Banco de Baterias
	private  int WBat;          // Potência de Carga / Descarga do Banco de Baterias
	private  int TBat;          // Temperatura do Banco de Baterias
	private  int SDBat;         // Valor de Saude das Baterias
	private  int IFontesCC12;   // Corrente de Saída das Fontes CC1 e CC2

	// Estados Água
	private	 int CircBoia;
	private  int BoiaCxAzul;
	private  int CircBomba;
	private  int AlRedeBomba;
	private  int BombaLigada;
	private  int CxAzNvBx;
	private  int EdCxAzCheia;
	private  int EstadoCxAz;

	// Medidas Água
	private  int TmpBmbLig;
	private  int TmpCxAzNvBx;

	// Estados do Inversor 1
	private  int Iv1Lig;
	private  int FalhaIv1;
	private  int SubTensaoInv1;
	private  int SobreTensaoInv1;
	private  int SobreCorrInv1;
	private  int SobreTempDrInv1;
	private  int SobreTempTrInv1;
	private  int DjAbIv1;
	private  int DJEINV1;

	// Estados do Inversor 2
	private  int Iv2Lig;
	private  int FalhaIv2;
	private  int SubTensaoInv2;
	private  int SobreTensaoInv2;
	private  int SobreCorrInv2;
	private  int SobreTempDrInv2;
	private  int SobreTempTrInv2;
	private  int DjAbIv2;
	private  int EstFonteCC;

	// Medidas da UTR2 - Comunicação com os Controladores de Carga
	private  int VP12;              // Medida 00: 0x3100 - PV array voltage 1
	private  int IS12;              // Medida 01: 0x3101 - PV array current 1
	private  int WS12;              // Medida 02: 0x3102 - PV array power 1
	private  int VBat1;             // Medida 03: 0x3104 - Battery voltage 1
	private  int ISCC1;             // Medida 04: 0x3105 - Battery charging current 1
	private  int WSCC1;             // Medida 05: 0x3106 - Battery charging power 1
	private  int VP34;              // Medida 08: 0x3100 - PV array voltage 2
	private  int IS34;              // Medida 09: 0x3101 - PV array current 2
	private  int WS34;              // Medida 10: 0x3102 - PV array power 2
	private  int VBat2;             // Medida 11: 0x3104 - Battery voltage 2
	private  int ISCC2;             // Medida 12: 0x3105 - Battery charging current 2
	private  int WSCC2;             // Medida 13: 0x3106 - Battery charging power 2 (Med[45])
	private  int SDCC1;             // Valor de Saude do Controlador de Carga 1
	private  int SDCC2;             // Valor de Saude do Controlador de Carga 2
	
	// Medidas da Geração
	private  int ITotGer;           // Corrente Total Gerada
	private  int WTotGer;           // Potência Total Gerada

	// Medidas do Inversor 2
	private  int IEIv2;             // Corrente de Entrada do Inversor 2
	private  int WEIv2;             // Potência de Entrada do Inversor 2
	private  int VSIv2;             // Tensão de Saída do Inversor 2
	private  int ISInv2;            // Corrente de Saída do Inversor 2
	private  int WSInv2;            // Potência de Saída do Inversor 2
	private  int TDInv2;            // Temperatura do Driver do Inversor 2
	private  int TTInv2;            // Temperatura do Transformador do Inversor 2
	private  int EfIv2;             // Eficiência do Inversor 2
	private  int SDIv2;             // Saúde do Inversor 2
	private  int EstrIv2;           // Estresse do Inversor 2

	// Medidas do Inversor 1
	private  int IEIv1;             // Corrente de Entrada do Inversor 1
	private  int WEIv1;             // Potência de Entrada do Inversor 1
	private  int VSIv1;             // Tensão de Saída do Inversor 1
	private  int ISInv1;            // Corrente de Saída do Inversor 1
	private  int WSInv1;            // Potência de Saída do Inversor 1
	private  int TDInv1;            // Temperatura do Driver do Inversor 1
	private  int TTInv1;            // Temperatura do Transformador do Inversor 1
	private  int EfIv1;             // Eficiência do Inversor 1
	private  int SDIv1;             // Saúde do Inversor 1
	private  int EstrIv1;           // Estresse do Inversor 1

	private  int Hora;
	private  int Minuto;
	private  int Segundo;
	private  int Dia;
	private  int Mes;
	private  int Ano;
	private  int EstComUTR;
	private  int EstComCC1;
	private  int EstComCC2;
	
	
	public  int getEstCom1() {
		return EstCom1;
	}

	public String getMsgXML() {
		return MsgXML;
	}
	
	
	//*****************************************************************************************************************
	//                                                                                                                *
	// Nome da Método: CarregaVariaveis                                                                               *
	//                                                                                                                *
	// Funcao: lê os dados de supervisão em formato binário (protocolo CoAP) e carrega nas variáveis do programa      *
	//                                                                                                                *
	//                                                                                                                *
	// Entrada: array int[] com a mensagem binária                                                                    *
	//                                                                                                                *
	// Saida: nao tem                                                                                                 *
	//                                                                                                                *
	//*****************************************************************************************************************
	//
	 public void CarregaVariaveis(int[] receiveData1) {
		 
		ComRecHTTP = "Atualiza";

		Hora = receiveData1[21];
		Minuto = receiveData1[22];
		Segundo = receiveData1[23];
		Dia = receiveData1[24];
		Mes = receiveData1[25];
		Ano = receiveData1[26];
        EstComUTR = receiveData1[27];
        EstComCC1 = receiveData1[28];
        EstComCC2 = receiveData1[29];
        EstCom1 = receiveData1[30];     // Estado de Comunicação do Conversor com o Concentrador Arduíno
		
		// Le as Entradas Digitais
		DJEINV1 = receiveData1[37];
		CircBoia = receiveData1[38];
		BoiaCxAzul = receiveData1[39];
		CircBomba = receiveData1[40];
		AlRedeBomba = receiveData1[41];
		EstRede = receiveData1[42];
		MdOp = receiveData1[43];
		MdCom = receiveData1[44];
		MdCtrl1 = receiveData1[55];
		MdCtrl = receiveData1[45];
		Carga1 = receiveData1[46];
		Carga2 = receiveData1[47];
		Carga3 = receiveData1[48];
		Carga4 = receiveData1[49];
		HabCom = receiveData1[50];
		EstadoInversor1 = receiveData1[51];
		EstadoInversor2 = receiveData1[52];
		EstadoCarga3 = receiveData1[53];
		BombaLigada = receiveData1[54];
	
		// Le os Alarmes
		FalhaIv1 = receiveData1[56];
		SubTensaoInv1 = receiveData1[57];
		SobreTensaoInv1 = receiveData1[58];
		SobreTempDrInv1 = receiveData1[59];
		SobreTempTrInv1 = receiveData1[60];
		DjAbIv1 = receiveData1[61];
		FalhaIv2 = receiveData1[62];
		SubTensaoInv2 = receiveData1[63];
		SobreTensaoInv2 = receiveData1[64];
		SobreTempDrInv2 = receiveData1[65];
		SobreTempTrInv2 = receiveData1[66];
		DjAbIv2 = receiveData1[67];
		
		CDBat = receiveData1[68];
		CxAzNvBx = receiveData1[69];
		EdCxAzCheia = receiveData1[70];
		FonteCC2Ligada = receiveData1[71];
		EstadoCxAz = receiveData1[72];
		FonteCC1Ligada = receiveData1[73];
		
		SobreCorrInv1 = receiveData1[74];
		SobreCorrInv2 = receiveData1[75];
    
		// Le o estado das saidas digitais
		int NumSd = 32;
		int[] SD = new int[NumSd];
		int k = 112;
		for (int i = 0; i < NumSd; i++){
			SD[i] = receiveData1[k];
			k = k + 1;
		}
	
		// Carrega as variaveis com os valores das saidas digitais da UTR1
		Iv1Lig = SD[1];
		CT2Inv = SD[17];
		CT1Inv = SD[0];
		CT3Inv = SD[2];
		Iv2Lig = SD[10];
		EstFonteCC = SD[16];
	 
		// Le as medidas de 1 byte da mensagem recebida
		EfIv1 = receiveData1[144];  // Eficiência do Inversor 1
		SDIv1 = receiveData1[145];  // Carrega
		SDIv2 = receiveData1[146];  // Carrega
		EfIv2 = receiveData1[147];  // Eficiência do Inversor 2
		SDCC1 = receiveData1[148];
		SDCC2 = receiveData1[149];
		SDBat = receiveData1[150];
		
		// Le as Medidas de 2 bytes da mensagem recebida
		int NumMed = 48;
		int[] Med = new int[NumMed];
		k = 160;
		for (byte i = 0; i < NumMed; i++){
			Med[i] = Util.DoisBytesInt(receiveData1[k], receiveData1[k + 1]);
			k = k + 2;
		}
		
		// Carrega as medidas lidas do Concentrador Arduino Mega nas variaveis
		VBat = Med[0];           // Tensão do Banco de Baterias
		VMBat = Med[16];         // Tensão Média Estendida do Banco de Baterias
		VRede = Med[5];          // Tensão da Rede
		Icarga3 = Med[14];       // Corrente Carga 3 (Geladeira)
		ICircCC = Med[3];        // Corrente Total dos Circuitos CC
		IFonteCC = Med[11];      // Corrente de Saída da Fonte CC
				
		TmpBmbLig = Med[17];     // Tempo da Bomba Ligada
		TmpCxAzNvBx = Med[46];   // Tempo da Caixa Azul em Nivel Baixo
				
		// Leitura e Cálculo das Medidas referentes à Geração e Consumo
		VP12 = Med[18];          // 0x3100 - PV array voltage 1
		IS12 = Med[19];          // 0x3101 - PV array current 1
		WS12 = Med[20];          // 0x3102 - PV array power 1
		VBat1 = Med[21];         // 0x3104 - Battery voltage 1
		ISCC1 = Med[22];         // 0x3105 - Battery charging current 1
		WSCC1 = Med[23];         // 0x3106 - Battery charging power 1
		TBat =  Med[24];         // 0x3110 - Battery Temperature 1
			
		VP34 = Med[26];          // 0x3100 - PV array voltage 2
		IS34 = Med[27];          // 0x3101 - PV array current 2
		WS34 = Med[28];          // 0x3102 - PV array power 2
		VBat2 = Med[29];         // 0x3104 - Battery voltage 2
		ISCC2 = Med[30];         // 0x3105 - Battery charging current 2
		WSCC2 = Med[31];         // 0x3106 - Battery charging power 2 (Med[45])
				
		ITotGer = Med[33];       					// Corrente Total Gerada
		WCircCC = Med[35];       					// Potencia Consumida pelos Circuitos de 24Vcc
		WFonteCC = Med[36];      					// Potencia Fornecida pela Fonte 24Vcc
		IBat = Med[37];          					// Corrente de Carga ou Descarga do Banco de Baterias
		WBat = (VBat * IBat)/100;					// Potência de Carga/Descarga do Banco de Baterias
		ITotGer = ISCC1 + ISCC2;					// Corrente Total Gerada
		WTotGer = WSCC1 + WSCC2;					// Potência Total Gerada 
		ITotCg = IEIv1 + IEIv2 + (ICircCC / 10);	// Corrente Total Consumida pelas Cargas
		WTotCg =  WEIv1 + WEIv2 + WCircCC;			// Potência Total Consumida pelas Cargas
				
		// Leitura e Cálculo das Medidas referentes ao Inversor 1
		IEIv1 = Med[12];         					// Corrente de Entrada do Inversor 1 (15)
		WEIv1 = (VBat * IEIv1)/100;					// Potência de Entrada do Inversor 1 (Med[41])
		VSIv1 = Med[4];          					// Tensão de Saída do Inversor 1
		ISInv1 = (7*Med[10])/10;   					// Corrente de Saída do Inversor 1 (13)
		WSInv1 = (VSIv1 * ISInv1)/1000;				// Potencia de Saida do Inversor 1 (Med[42])
		TDInv1 = Med[8];         					// Temperatura do Driver do Inversor 1 (2)
		TTInv1 = Med[9];         					// Temperatura do Transformador do Inversor 1 (7)
		if (WEIv1 > 2000) {
			EfIv1 = (100 * WSInv1) / WEIv1;			// Eficiência do Inversor 1
		}
		else {
			EfIv1 = 0;
		}
				
		// Leitura e Cálculo das Medidas referentes ao Inversor 2
		IEIv2 = Med[15];         					// Corrente de Entrada do Inversor 2 (12)
		WEIv2 = (VBat * IEIv2) / 100;         		// Potencia de Entrada do Inversor 2 (Med[38])
		VSIv2 = Med[6];          					// Tensão de Saída do Inversor 2
		ISInv2 = Med[13];        					// Corrente de Saída do Inversor 2 (10)
		WSInv2 = (VSIv2 * ISInv2) / 1000;       	// Potencia de Saida do Inversor 2 (Med[39])
		TDInv2 = Med[2];         					// Temperatura do Driver do Inversor 2 (8)
		TTInv2 = Med[7];         					// Temperatura do Transformador do Inversor 2 (9)
		if (WEIv2 > 2000) {
			EfIv2 = (100 * WSInv2) / WEIv2;			// Eficiência do Inversor 2
		}
		else {
			EfIv2 = 0;
		}
		
	} // Fim do Método
	
	
	//**********************************************************************************************************************
	// Nome do Método: MontaXML                                                                                            *
    //	                                                                                                                   *
	// Data: 04/09/2021                                                                                                    *
	//                                                                                                                     *
	// Funcao: lê as variáveis de supervisão, carrega nas variáveis, calcula o valor caso seja necessário, e monta uma     *
	//         string contendo uma mensagem em formato XML com todos os valores das variáveis atualizados.                 *
	//                                                                                                                     *
	// Entrada: array int[] contendo a mensagem binária em protocolo CoAP                                                  *                                                                                                    *
	//                                                                                                                     *
	// Saida: string com a mensagem XML de resposta                                                                        *
    //	                                                                                                                   *
	//**********************************************************************************************************************
	//
	public  String MontaXML() {
		
		//String EndIP1;
		String StrEstCom1;
		String StrEstComUTR;
		String StrEstComCC1;
		String StrEstComCC2;
		String StrMdOp;
		String StrMdCom;
		String StrMdCtrl1;
		String StrMdCtrl;
		String StrCT2Inv;
		String StrCT1Inv;
		String StrCT3Inv;
		String StrEstCxAzul;
		String StrNivCxAzul;
		String StrEstAlimBoia;
		String StrAlRedeBomba;
		String StrIv1Lig;
		String StrEstBomba;
		String StrEstFonteCC1;
		String StrEstFonteCC2;
		String StrEstIv2;
		String StrEstVSIv2;
		String StrEstTDIv2;
		String StrEstTTIv2;
		String StrEstIv1;
		String StrEstVSIv1;
		String CorTDIv2;
		String CorTTIv2;
		String CorTDIv1;
		String CorTTIv1;
		String StrEstRede;
		String StrEstValCg3;
		String StrEstValVBat;
		String StrEstIBat;
		String CorTBat;
		String StrSaudeBat;
		String StrValVP12;
		String StrValVP34;
		
		// Estados de Comunicacao
		StrEstCom1 = "Falha";
		if (EstCom1 == 1) { StrEstCom1 = "Normal"; }
			
		StrEstComUTR = "Falha";
		if (EstComUTR == 1) { StrEstComUTR = "Normal"; }
		
		StrEstComCC1 = "Falha";
		if (EstComCC1 == 1) { StrEstComCC1 = "Normal"; }
		
		StrEstComCC2 = "Falha";
		if (EstComCC2 == 1) { StrEstComCC2 = "Normal"; }
		
		// Estados Gerais
		StrMdOp = "Economia";
		if (MdOp == 1) {	StrMdOp = "Normal";	}

		StrMdCom = "Local";
		if (MdCom == 1) { StrMdCom = "Remoto"; }
		
		StrMdCtrl1 = "Manual";
		if (MdCtrl1 == 1) { StrMdCtrl1 = "Automatico"; }
		
		StrMdCtrl = "Manual";
		if (MdCtrl == 1) { StrMdCtrl = "Automatico"; }

		StrCT2Inv = "Rede";                // Fonte de Energia Carga 1
		if (CT2Inv == 1) {
			StrCT2Inv = "Inversor 2"; 
		}
		else {
			if (Carga1 == 1) {
				StrCT2Inv = "Rede (Hab)";
			}
		}
		
		StrCT1Inv = "Rede";                // Fonte de Energia Carga 2
		if (CT1Inv == 1) { StrCT1Inv = "Inversor 2"; }
		else { if (Carga2 == 1) { StrCT1Inv = "Rede (Hab)"; } }
		
		StrCT3Inv = "Rede";                // Fonte de Energia Carga 3
		if (CT3Inv == 1) { StrCT3Inv = "Inversor 2"; }
		else { if (Carga3 == 1) { StrCT3Inv = "Rede (Hab)"; } }
		
		StrEstCxAzul = "";
		StrNivCxAzul = "";
		switch (EstadoCxAz) {
		
			case 0:  //  EstadoCxAz = 0 => Estado da Caixa Azul = Indefinido 
				StrEstCxAzul = "Indefinido";
				StrNivCxAzul = "Indefinido";
			break;
		
			case 1:  //  EstadoCxAz = 1 => Estado da Caixa Azul = Precisa Encher Nivel Baixo
				StrEstCxAzul = "Precisa Encher";
				StrNivCxAzul = "Baixo";
			break;
	    
			case 2:  //  EstadoCxAz = 2 => Estado da Caixa Azul = Precisa Encher Nivel Normal
				StrEstCxAzul = "Precisa Encher";
				StrNivCxAzul = "Normal";
			break;
	    
			case 3:  //  EstadoCxAz = 3 => Estado da Caixa Azul = Cheia
				StrEstCxAzul = "Cheia";
				StrNivCxAzul = "Normal";
			break;
	    
			case 4:  //  EstadoCxAz = 4 => Estado da Caixa Azul = Falha de Sinalizacao 1
				StrEstCxAzul = "Falha Boia";
				StrNivCxAzul = "";
			break;
	    
			case 5:  // EstadoCxAz = 5 => Estado da Caixa Azul = Falha de Sinalizacao 2
				StrEstCxAzul = "Falha Boia";
				StrNivCxAzul = "";
			break;
		}
		
		StrEstAlimBoia = "";
		if (CircBoia == 1) { StrEstAlimBoia = "Ligado"; }
		else { StrEstAlimBoia = "Desligado"; }
		
		StrAlRedeBomba = "";
		if (EstRede == 1) {
			if (AlRedeBomba == 1) { StrAlRedeBomba = "Ligado"; }
			else { StrAlRedeBomba = "Desligado"; }
		}
		else {
			StrAlRedeBomba = "Falta CA";
		}
		
		StrIv1Lig = "Rede";                		// Fonte de energia da bomba
		if (Iv1Lig == 1) {
			StrIv1Lig = "Inversor 1"; 
		}
		else {
			if (Carga4 == 1) {
				StrIv1Lig = "Rede (Hab)";	
			}
		}
		
		StrEstBomba = "Desligada";              // Estado da alimentação da bomba
		if (CircBomba == 1) { StrEstBomba = "Ligada"; }
		
		StrEstFonteCC1 = "";            		// Estado das Fontes CC1 e CC2
		StrEstFonteCC2 = "";
		if (EstRede == 1) {                 	// Se a tensao da Rede esta OK,
			if (FonteCC1Ligada == 1) {      	// e se a fonte CC1 está fornecendo tensão,
				StrEstFonteCC1 = "Ligada";     	// Carrega a mensagem de que a fonte CC1 está ligada
			}
			else {                             	// Se a fonte CC1 não está fornecendo tensão,
				StrEstFonteCC1 = "Desligada";  	// Carrega a mensagem de que a fonte CC1 está desligada
			}
			if (FonteCC2Ligada == 1) {      	// e se a fonte CC2 está fornecendo tensão,
				StrEstFonteCC2 = "Ligada";     	// Carrega a mensagem de que a fonte CC1 está ligada
			}
			else {                             	// Se a fonte CC1 não está fornecendo tensão,
				StrEstFonteCC2 = "Desligada";  	// Carrega a mensagem de que a fonte CC1 está desligada
			}
		}
		else {                                 	// Se falta CA,
			if (FonteCC1Ligada == 0) {      	// e se a saida da fonte está sem tensao,
				StrEstFonteCC1 = "Falta CA";   	// Carrega a mensagem de que Falta CA
			}
			else {
				StrEstFonteCC1 = "Falha";      	// Carrega a mensagem de Falha
			}
			if (FonteCC2Ligada == 0) {      	// e se a saida da fonte está sem tensao,
				StrEstFonteCC2 = "Falta CA";   	// Carrega a mensagem de que Falta CA
			}
			else {
				StrEstFonteCC2 = "Falha";      	// Carrega a mensagem de Falha
			}
		}
		
		StrEstIv2 = "Desligado";
		StrEstVSIv2 = "      ";
		if (Iv2Lig == 1) { 
			StrEstIv2 = "Ligado";
			if (VSIv2 < 21000) { StrEstVSIv2 = "Baixa"; }
			if ((VSIv2 >= 21000) && (VSIv2 <= 22500)) { StrEstVSIv2 = "Normal"; }
			if (VSIv2 > 22500) { StrEstVSIv2 = "Alta"; }
		}
		else {
			IEIv2 = 0;
			WEIv2 = 0;
			ISInv2 = 0;
			WSInv2 = 0;
		}
		
		StrEstTDIv2 = "          ";
		if (TDInv2 < 4600) { StrEstTDIv2 = "Normal";	}
		if ((TDInv2 >= 4600) && (TDInv2 < 5000)) { StrEstTDIv2 = "Alta"; }
		if (TDInv2 >= 5000) { StrEstTDIv2 = "Muito Alta"; }
		
		StrEstTTIv2 = "          ";
		if (TTInv2 < 4600) { StrEstTTIv2 = "Normal";	}
		if ((TTInv2 >= 4600) && (TTInv2 < 5000)) { StrEstTTIv2 = "Alta"; }
		if (TTInv2 >= 5000) { StrEstTTIv2 = "Muito Alta"; }
		
		StrEstIv1 = "Desligado";
		StrEstVSIv1 = "      ";
		if (Iv1Lig == 1) {
			StrEstIv1 = "Ligado";
			if (VSIv1 < 17500) { StrEstVSIv1 = "Baixa"; }
			if ((VSIv1 >= 17500) && (VSIv1 <= 20000)) { StrEstVSIv1 = "Normal"; }
			if (VSIv1 > 20000) { StrEstVSIv1 = "Alta"; }
		}
		else {
			IEIv1 = 0;
			WEIv1 = 0;
			ISInv1 = 0;
			WSInv1 = 0;
		}
		
		CorTDIv2 = "";
		if (TDInv2 >= 5000) { CorTDIv2 = "style='color:red;'"; }
		CorTTIv2 = "";
		if (TTInv2 >= 5000) { CorTTIv2 = "style='color:red;'"; }
		
		CorTDIv1 = "";
		if (TDInv1 >= 5000) { CorTDIv1 = "style='color:red;'"; }
		CorTTIv1 = "";
		if (TTInv1 >= 5000) { CorTTIv1 = "style='color:red;'"; }
		
		StrEstRede = "";
		if (EstRede == 1) {
			if (VRede > 19000) { StrEstRede = "Normal"; }
			else { StrEstRede = "(Baixa)"; }
		}
		else { StrEstRede = "Falta CA"; }
		
		StrEstValCg3 = "         ";
		if (Icarga3 < 100) { StrEstValCg3 = "Deslig"; }
		if (Icarga3 > 400) { StrEstValCg3 = "Ligada"; }
		
		StrEstValVBat = "           ";
		if (VBat < 2300) { StrEstValVBat = "Baixa"; }
		if ((VBat >= 2300) && (VBat < 2640)) { StrEstValVBat = "Carga/Desc.";	}		
		if ((VBat >= 2640) && (VBat <= 2760)) { StrEstValVBat = "Flutuação"; }
		if ((VBat > 2760) && (VBat < 2900)) { StrEstValVBat = "Equalização"; }
		if (VBat > 2900) { StrEstValVBat = "Alta"; }
		
		StrEstIBat = "        ";
		if (CDBat == 0) { StrEstIBat = "Descarga"; }
		else { StrEstIBat = "Carga"; }
		
		CorTBat = "";
		if (TBat > 4000) { CorTBat = "style='color:red;'"; }
		
		StrSaudeBat = "Normal";
		if (SDBat < 85) { StrSaudeBat = "Atenção"; }
		
		StrValVP12 = "      ";
		if (VP12 < 3000) { StrValVP12 = "Baixa"; }
		if (VP12 >= 3000) { StrValVP12 = "Normal"; }
		
		StrValVP34 = "      ";
		if (VP34 < 3000) { StrValVP34 = "Baixa"; }
		if (VP34 >= 3000) { StrValVP34 = "Normal"; }
		
		// Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis de supervisão
		String MsgXMLArray[][][][] = new String[1][10][30][2];
		int IdNv0 = 0;
		int IdNv1 = 0;
		MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "04"; 
			
		IdNv1 = 1; // Grupo de 19 Variáveis de Informação GERAL                             
		MsgXMLArray[IdNv0][IdNv1][0][0] = "GERAL";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "21";
			
		MsgXMLArray[IdNv0][IdNv1][1][0] = "COMCNV";
		MsgXMLArray[IdNv0][IdNv1][1][1] = "Normal";
		MsgXMLArray[IdNv0][IdNv1][2][0] = "COMCNC";
		MsgXMLArray[IdNv0][IdNv1][2][1] = StrEstCom1;
		MsgXMLArray[IdNv0][IdNv1][3][0] = "COMUTR";
		MsgXMLArray[IdNv0][IdNv1][3][1] = StrEstComUTR;
		MsgXMLArray[IdNv0][IdNv1][4][0] = "COMCC1";
		MsgXMLArray[IdNv0][IdNv1][4][1] = StrEstComCC1;
		MsgXMLArray[IdNv0][IdNv1][5][0] = "COMCC2";
		MsgXMLArray[IdNv0][IdNv1][5][1] = StrEstComCC2;
		MsgXMLArray[IdNv0][IdNv1][6][0] = "CLK";
		MsgXMLArray[IdNv0][IdNv1][6][1] = Util.ImpHora(Hora, Minuto, Segundo);
		MsgXMLArray[IdNv0][IdNv1][7][0] = "DATA";
		MsgXMLArray[IdNv0][IdNv1][7][1] = Util.ImpData(Dia, Mes, Ano); 
		MsgXMLArray[IdNv0][IdNv1][8][0] = "CMDEX";
		MsgXMLArray[IdNv0][IdNv1][8][1] = ComRecHTTP;
		MsgXMLArray[IdNv0][IdNv1][9][0] = "MDOP";
		MsgXMLArray[IdNv0][IdNv1][9][1] = StrMdOp;
		MsgXMLArray[IdNv0][IdNv1][10][0] = "MDCOM";
		MsgXMLArray[IdNv0][IdNv1][10][1] = StrMdCom;
		MsgXMLArray[IdNv0][IdNv1][11][0] = "MDCT1";
		MsgXMLArray[IdNv0][IdNv1][11][1] = StrMdCtrl1;
		MsgXMLArray[IdNv0][IdNv1][12][0] = "MDCT234";
		MsgXMLArray[IdNv0][IdNv1][12][1] = StrMdCtrl;
		MsgXMLArray[IdNv0][IdNv1][13][0] = "ENCG1";
		MsgXMLArray[IdNv0][IdNv1][13][1] = StrCT2Inv;
		MsgXMLArray[IdNv0][IdNv1][14][0] = "ENCG2";
		MsgXMLArray[IdNv0][IdNv1][14][1] = StrCT1Inv;
		MsgXMLArray[IdNv0][IdNv1][15][0] = "ENCG3";
		MsgXMLArray[IdNv0][IdNv1][15][1] = StrCT3Inv;
		MsgXMLArray[IdNv0][IdNv1][16][0] = "ICG3";
		MsgXMLArray[IdNv0][IdNv1][16][1] = Util.FrmAna3(Icarga3," A");
		MsgXMLArray[IdNv0][IdNv1][17][0] = "VBAT";
		MsgXMLArray[IdNv0][IdNv1][17][1] = Util.FrmAna(VBat," V");
		MsgXMLArray[IdNv0][IdNv1][18][0] = "VREDE";
		MsgXMLArray[IdNv0][IdNv1][18][1] = Util.FrmAna(VRede," V");
		MsgXMLArray[IdNv0][IdNv1][19][0] = "ESTVRD";
		MsgXMLArray[IdNv0][IdNv1][19][1] = StrEstRede;
		MsgXMLArray[IdNv0][IdNv1][20][0] = "TBAT";
		MsgXMLArray[IdNv0][IdNv1][20][1] = Util.FrmAna(TBat,"°C");
		MsgXMLArray[IdNv0][IdNv1][21][0] = "SDBAT";
		MsgXMLArray[IdNv0][IdNv1][21][1] = Util.FrmAnaInt(SDBat," %");
		
		IdNv1 = 2; // Grupo de 07 Variáveis de Informação da Bomba do Poço e da Caixa Azul
		MsgXMLArray[IdNv0][IdNv1][0][0] = "AGUA";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "07";
			
		MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTCXAZ";
		MsgXMLArray[IdNv0][IdNv1][1][1] = StrEstCxAzul;
		MsgXMLArray[IdNv0][IdNv1][2][0] = "NIVCXAZ";
		MsgXMLArray[IdNv0][IdNv1][2][1] = StrNivCxAzul;
		MsgXMLArray[IdNv0][IdNv1][3][0] = "ESTBMB";
		MsgXMLArray[IdNv0][IdNv1][3][1] = StrEstBomba;
		MsgXMLArray[IdNv0][IdNv1][4][0] = "ESTDJB";
		MsgXMLArray[IdNv0][IdNv1][4][1] = StrEstAlimBoia;
		MsgXMLArray[IdNv0][IdNv1][5][0] = "ESTDJRB";
		MsgXMLArray[IdNv0][IdNv1][5][1] = StrAlRedeBomba;
		MsgXMLArray[IdNv0][IdNv1][6][0] = "ENBMB";
		MsgXMLArray[IdNv0][IdNv1][6][1] = StrIv1Lig;
		MsgXMLArray[IdNv0][IdNv1][7][0] = "TMPBL";
		MsgXMLArray[IdNv0][IdNv1][7][1] = Util.FormAnaHora(TmpBmbLig);
			
		IdNv1 = 3; // Grupo de 18 Variáveis de Informação da Geração Solar e do Consumo
		MsgXMLArray[IdNv0][IdNv1][0][0] = "GERCONS";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "18";
		
		MsgXMLArray[IdNv0][IdNv1][1][0] = "VP12";
		MsgXMLArray[IdNv0][IdNv1][1][1] = Util.FrmAna(VP12," V");
		MsgXMLArray[IdNv0][IdNv1][2][0] = "IS12";
		MsgXMLArray[IdNv0][IdNv1][2][1] = Util.FrmAna(IS12," A");
		MsgXMLArray[IdNv0][IdNv1][3][0] = "ISCC1";
		MsgXMLArray[IdNv0][IdNv1][3][1] = Util.FrmAna(ISCC1," A");
		MsgXMLArray[IdNv0][IdNv1][4][0] = "WSCC1";
		MsgXMLArray[IdNv0][IdNv1][4][1] = Util.FrmAna(WSCC1," W");
		MsgXMLArray[IdNv0][IdNv1][5][0] = "SDCC1";
		MsgXMLArray[IdNv0][IdNv1][5][1] = Util.FrmAnaInt(SDCC1," %");
		MsgXMLArray[IdNv0][IdNv1][6][0] = "VP34";
		MsgXMLArray[IdNv0][IdNv1][6][1] = Util.FrmAna(VP34," V");
		MsgXMLArray[IdNv0][IdNv1][7][0] = "IS34";
		MsgXMLArray[IdNv0][IdNv1][7][1] = Util.FrmAna(IS34," A");
		MsgXMLArray[IdNv0][IdNv1][8][0] = "ISCC2"; 
		MsgXMLArray[IdNv0][IdNv1][8][1] = Util.FrmAna(ISCC2," A");
		MsgXMLArray[IdNv0][IdNv1][9][0] = "WSCC2";
		MsgXMLArray[IdNv0][IdNv1][9][1] = Util.FrmAna(WSCC2," W");
		MsgXMLArray[IdNv0][IdNv1][10][0] = "SDCC2";
		MsgXMLArray[IdNv0][IdNv1][10][1] = Util.FrmAnaInt(SDCC2," %");
		MsgXMLArray[IdNv0][IdNv1][11][0] = "ITOTGER";
		MsgXMLArray[IdNv0][IdNv1][11][1] = Util.FrmAna(ITotGer," A");
		MsgXMLArray[IdNv0][IdNv1][12][0] = "WTOTGER";
		MsgXMLArray[IdNv0][IdNv1][12][1] = Util.FrmAna(WTotGer," W");
		MsgXMLArray[IdNv0][IdNv1][13][0] = "ITOTCG";
		MsgXMLArray[IdNv0][IdNv1][13][1] = Util.FrmAna(ITotCg," A");
		MsgXMLArray[IdNv0][IdNv1][14][0] = "WTOTCG";
		MsgXMLArray[IdNv0][IdNv1][14][1] = Util.FrmAna(WTotCg," W");
		MsgXMLArray[IdNv0][IdNv1][15][0] = "ESTFT1";
		MsgXMLArray[IdNv0][IdNv1][15][1] = StrEstFonteCC1;
		MsgXMLArray[IdNv0][IdNv1][16][0] = "ESTFT2";
		MsgXMLArray[IdNv0][IdNv1][16][1] = StrEstFonteCC2;
		MsgXMLArray[IdNv0][IdNv1][17][0] = "ICIRCC";
		MsgXMLArray[IdNv0][IdNv1][17][1] = Util.FrmAna3(ICircCC," A");
		MsgXMLArray[IdNv0][IdNv1][18][0] = "WCIRCC";
		MsgXMLArray[IdNv0][IdNv1][18][1] = Util.FrmAna(WCircCC," W");
			
		IdNv1 = 4; // Grupo de 20 Variáveis de Informação dos Inversores 1 e 2
        MsgXMLArray[IdNv0][IdNv1][0][0] = "INV";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "20";
            
		MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTIV2";
		MsgXMLArray[IdNv0][IdNv1][1][1] = StrEstIv2;
		MsgXMLArray[IdNv0][IdNv1][2][0] = "IEIV2";
		MsgXMLArray[IdNv0][IdNv1][2][1] = Util.FrmAna(IEIv2," A");
		MsgXMLArray[IdNv0][IdNv1][3][0] = "WEIV2";
		MsgXMLArray[IdNv0][IdNv1][3][1] = Util.FrmAna(WEIv2," W");
		MsgXMLArray[IdNv0][IdNv1][4][0] = "VSIV2";
		MsgXMLArray[IdNv0][IdNv1][4][1] = Util.FrmAna(VSIv2," V");
		MsgXMLArray[IdNv0][IdNv1][5][0] = "ISIV2";
		MsgXMLArray[IdNv0][IdNv1][5][1] = Util.FrmAna3(ISInv2," A");
		MsgXMLArray[IdNv0][IdNv1][6][0] = "WSIV2";
		MsgXMLArray[IdNv0][IdNv1][6][1] = Util.FrmAna(WSInv2," W");
		MsgXMLArray[IdNv0][IdNv1][7][0] = "TDIV2";
		MsgXMLArray[IdNv0][IdNv1][7][1] = Util.FrmAna(TDInv2," C");
		MsgXMLArray[IdNv0][IdNv1][8][0] = "TTIV2"; 
		MsgXMLArray[IdNv0][IdNv1][8][1] = Util.FrmAna(TTInv2," C");
		MsgXMLArray[IdNv0][IdNv1][9][0] = "EFIV2";
		MsgXMLArray[IdNv0][IdNv1][9][1] = Util.FrmAnaInt(EfIv2," %");
		MsgXMLArray[IdNv0][IdNv1][10][0] = "SDIV2";
		MsgXMLArray[IdNv0][IdNv1][10][1] = Util.FrmAnaInt(SDIv2," %");
			
		MsgXMLArray[IdNv0][IdNv1][11][0] = "ESTIV1";
		MsgXMLArray[IdNv0][IdNv1][11][1] = StrEstIv1;
		MsgXMLArray[IdNv0][IdNv1][12][0] = "IEIV1";
		MsgXMLArray[IdNv0][IdNv1][12][1] = Util.FrmAna(IEIv1," A");
		MsgXMLArray[IdNv0][IdNv1][13][0] = "WEIV1";
		MsgXMLArray[IdNv0][IdNv1][13][1] = Util.FrmAna(WEIv1," W");
		MsgXMLArray[IdNv0][IdNv1][14][0] = "VSIV1";
		MsgXMLArray[IdNv0][IdNv1][14][1] = Util.FrmAna(VSIv1," V");
		MsgXMLArray[IdNv0][IdNv1][15][0] = "ISIV1";
		MsgXMLArray[IdNv0][IdNv1][15][1] = Util.FrmAna3(ISInv1," A");
		MsgXMLArray[IdNv0][IdNv1][16][0] = "WSIV1";
		MsgXMLArray[IdNv0][IdNv1][16][1] = Util.FrmAna(WSInv1," W");
		MsgXMLArray[IdNv0][IdNv1][17][0] = "TDIV1";
		MsgXMLArray[IdNv0][IdNv1][17][1] = Util.FrmAna(TDInv1," C");
		MsgXMLArray[IdNv0][IdNv1][18][0] = "TTIV1";
		MsgXMLArray[IdNv0][IdNv1][18][1] = Util.FrmAna(TTInv1," C");
		MsgXMLArray[IdNv0][IdNv1][19][0] = "EFIV1";
		MsgXMLArray[IdNv0][IdNv1][19][1] = Util.FrmAnaInt(EfIv1," %");
		MsgXMLArray[IdNv0][IdNv1][20][0] = "SDIV1";
		MsgXMLArray[IdNv0][IdNv1][20][1] = Util.FrmAnaInt(SDIv1," %");
		
		// Retorna a Mensagem XML completa em formato de String
		MsgXML = StringXML(MsgXMLArray) + " ";
		return(MsgXML);
		
	} // Fim do Método
	
	
	//**********************************************************************************************************************
	// Nome do Método: MontaXMLFalha()                                                                                     *
    //	                                                                                                                   *
	// Data: 10/01/2020                                                                                                    *
	//                                                                                                                     *
	// Funcao: monta uma string XML indicando falha                                                                        *
	// Entrada: int: 0 = COMCNC = "----------" / 1 = COMCNC = "Falha"                                                      *
	//                                                                                                                     *
	// Saida: string com a mensagem XML                                                                                    *
    //	                                                                                                                   *
	//**********************************************************************************************************************
	//
	public  String MontaXMLFalha(int Opcao) {
		
		// Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis de supervisão
		String MsgXMLArray[][][][] = new String[1][10][30][2];
		int IdNv0 = 0;
		int IdNv1 = 0;
		MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "04"; 
			
		IdNv1 = 1; // Grupo de 19 Variáveis de Informação GERAL                             
		MsgXMLArray[IdNv0][IdNv1][0][0] = "GERAL";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "21";
			
		MsgXMLArray[IdNv0][IdNv1][1][0] = "COMCNV";
		MsgXMLArray[IdNv0][IdNv1][1][1] = "Falha";
		MsgXMLArray[IdNv0][IdNv1][2][0] = "COMCNC";
		if (Opcao == 0) {
			MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
		}
		else {
			MsgXMLArray[IdNv0][IdNv1][2][1] = "Falha";
		}
		MsgXMLArray[IdNv0][IdNv1][3][0] = "COMUTR";
		MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][4][0] = "COMCC1";
		MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][5][0] = "COMCC2";
		MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][6][0] = "CLK";
		MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][7][0] = "DATA";
		MsgXMLArray[IdNv0][IdNv1][7][1] = "----------"; 
		MsgXMLArray[IdNv0][IdNv1][8][0] = "CMDEX";
		MsgXMLArray[IdNv0][IdNv1][8][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][9][0] = "MDOP";
		MsgXMLArray[IdNv0][IdNv1][9][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][10][0] = "MDCOM";
		MsgXMLArray[IdNv0][IdNv1][10][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][11][0] = "MDCT1";
		MsgXMLArray[IdNv0][IdNv1][11][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][12][0] = "MDCT234";
		MsgXMLArray[IdNv0][IdNv1][12][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][13][0] = "ENCG1";
		MsgXMLArray[IdNv0][IdNv1][13][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][14][0] = "ENCG2";
		MsgXMLArray[IdNv0][IdNv1][14][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][15][0] = "ENCG3";
		MsgXMLArray[IdNv0][IdNv1][15][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][16][0] = "ICG3";
		MsgXMLArray[IdNv0][IdNv1][16][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][17][0] = "VBAT";
		MsgXMLArray[IdNv0][IdNv1][17][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][18][0] = "VREDE";
		MsgXMLArray[IdNv0][IdNv1][18][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][19][0] = "ESTVRD";
		MsgXMLArray[IdNv0][IdNv1][19][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][20][0] = "TBAT";
		MsgXMLArray[IdNv0][IdNv1][20][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][21][0] = "SDBAT";
		MsgXMLArray[IdNv0][IdNv1][21][1] = "----------";
		
		IdNv1 = 2; // Grupo de 07 Variáveis de Informação da Bomba do Poço e da Caixa Azul
		MsgXMLArray[IdNv0][IdNv1][0][0] = "AGUA";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "07";
			
		MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTCXAZ";
		MsgXMLArray[IdNv0][IdNv1][1][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][2][0] = "NIVCXAZ";
		MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][3][0] = "ESTBMB";
		MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][4][0] = "ESTDJB";
		MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][5][0] = "ESTDJRB";
		MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][6][0] = "ENBMB";
		MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][7][0] = "TMPBL";
		MsgXMLArray[IdNv0][IdNv1][7][1] = "----------";
			
		IdNv1 = 3; // Grupo de 18 Variáveis de Informação da Geração Solar e do Consumo
		MsgXMLArray[IdNv0][IdNv1][0][0] = "GERCONS";
		MsgXMLArray[IdNv0][IdNv1][0][1] = "18";
		
		MsgXMLArray[IdNv0][IdNv1][1][0] = "VP12";
		MsgXMLArray[IdNv0][IdNv1][1][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][2][0] = "IS12";
		MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][3][0] = "ISCC1";
		MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][4][0] = "WSCC1";
		MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][5][0] = "SDCC1";
		MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][6][0] = "VP34";
		MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][7][0] = "IS34";
		MsgXMLArray[IdNv0][IdNv1][7][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][8][0] = "ISCC2"; 
		MsgXMLArray[IdNv0][IdNv1][8][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][9][0] = "WSCC2";
		MsgXMLArray[IdNv0][IdNv1][9][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][10][0] = "SDCC2";
		MsgXMLArray[IdNv0][IdNv1][10][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][11][0] = "ITOTGER";
		MsgXMLArray[IdNv0][IdNv1][11][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][12][0] = "WTOTGER";
		MsgXMLArray[IdNv0][IdNv1][12][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][13][0] = "ITOTCG";
		MsgXMLArray[IdNv0][IdNv1][13][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][14][0] = "WTOTCG";
		MsgXMLArray[IdNv0][IdNv1][14][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][15][0] = "ESTFT1";
		MsgXMLArray[IdNv0][IdNv1][15][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][16][0] = "ESTFT2";
		MsgXMLArray[IdNv0][IdNv1][16][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][17][0] = "ICIRCC";
		MsgXMLArray[IdNv0][IdNv1][17][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][18][0] = "WCIRCC";
		MsgXMLArray[IdNv0][IdNv1][18][1] = "----------";
			
		IdNv1 = 4; // Grupo de 20 Variáveis de Informação dos Inversores 1 e 2
        MsgXMLArray[IdNv0][IdNv1][0][0] = "INV";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "20";
            
		MsgXMLArray[IdNv0][IdNv1][1][0] = "ESTIV2";
		MsgXMLArray[IdNv0][IdNv1][1][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][2][0] = "IEIV2";
		MsgXMLArray[IdNv0][IdNv1][2][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][3][0] = "WEIV2";
		MsgXMLArray[IdNv0][IdNv1][3][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][4][0] = "VSIV2";
		MsgXMLArray[IdNv0][IdNv1][4][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][5][0] = "ISIV2";
		MsgXMLArray[IdNv0][IdNv1][5][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][6][0] = "WSIV2";
		MsgXMLArray[IdNv0][IdNv1][6][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][7][0] = "TDIV2";
		MsgXMLArray[IdNv0][IdNv1][7][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][8][0] = "TTIV2"; 
		MsgXMLArray[IdNv0][IdNv1][8][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][9][0] = "EFIV2";
		MsgXMLArray[IdNv0][IdNv1][9][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][10][0] = "SDIV2";
		MsgXMLArray[IdNv0][IdNv1][10][1] = "----------";
			
		MsgXMLArray[IdNv0][IdNv1][11][0] = "ESTIV1";
		MsgXMLArray[IdNv0][IdNv1][11][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][12][0] = "IEIV1";
		MsgXMLArray[IdNv0][IdNv1][12][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][13][0] = "WEIV1";
		MsgXMLArray[IdNv0][IdNv1][13][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][14][0] = "VSIV1";
		MsgXMLArray[IdNv0][IdNv1][14][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][15][0] = "ISIV1";
		MsgXMLArray[IdNv0][IdNv1][15][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][16][0] = "WSIV1";
		MsgXMLArray[IdNv0][IdNv1][16][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][17][0] = "TDIV1";
		MsgXMLArray[IdNv0][IdNv1][17][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][18][0] = "TTIV1";
		MsgXMLArray[IdNv0][IdNv1][18][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][19][0] = "EFIV1";
		MsgXMLArray[IdNv0][IdNv1][19][1] = "----------";
		MsgXMLArray[IdNv0][IdNv1][20][0] = "SDIV1";
		MsgXMLArray[IdNv0][IdNv1][20][1] = "----------";
		
		// Retorna a Mensagem XML completa em formato de String
		return(StringXML(MsgXMLArray));
		
	} // Fim do Método
	
	
	//***************************************************************************************************************************
    //                                                                                                                          *
	// Nome do Método: StringXML()                                                                                              *
	//	                                                                                                                        *
	// Funcao: monta uma String com a mensagem XML de resposta inserindo o valor das variáveis                                  *
    //                                                                                                                          *
	// Entrada: array String com as Tags dos Níveis 0, 1 e 2 e os valores das variáveis de supervisão                           *
    //                                                                                                                          *
	// Saida: String com a mensagem XML                                                                                         *
	//	                                                                                                                        *
	//***************************************************************************************************************************
	//
	 String StringXML(String MsgXMLArray[][][][]) {
		String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";  // Imprime a linha de Versão e Codificação de Caracteres  
		MsgXML = MsgXML + "<" + MsgXMLArray[0][0][0][0] + ">\n";         // Imprime a Tag de Nivel 0

		char Dezena = MsgXMLArray[0][0][0][1].charAt(0);
		char Unidade = MsgXMLArray[0][0][0][1].charAt(1);
		int NmTagNv1 = Util.TwoCharToInt(Dezena, Unidade);               // Obtem o Numero de Tags de Nivel 1
		
		for (int i = 1; i <= NmTagNv1; i++){                             // Repete até imprimir todas as Tags de Nível 1 e Nível 2
			MsgXML = MsgXML + "  <" + MsgXMLArray[0][i][0][0] + ">\n";   // Imprime a Tag de Nivel 1 de Início do Grupo
			char DzNumVar = MsgXMLArray[0][i][0][1].charAt(0);
			char UnNumVar = MsgXMLArray[0][i][0][1].charAt(1);
			int NumVar = Util.TwoCharToInt(DzNumVar, UnNumVar);          // Obtém o Número de Variáveis do Grupo
		    
			for (int j = 1; j <= NumVar; j++){                           // Repeta até imprimir todas as Tags de Nível 2 e suas variáveis
				MsgXML = MsgXML + "    <"+MsgXMLArray[0][i][j][0]+">" +  // Imprime as Tags de Nível 2 e os Valores das Variáveis 
			                              MsgXMLArray[0][i][j][1] +
			                         "</"+MsgXMLArray[0][i][j][0]+">\n";
			}
			MsgXML = MsgXML + "  </" + MsgXMLArray[0][i][0][0] + ">\n";  // Imprime a Tag de Nivel 1 de Fim de Grupo
		}
		MsgXML = MsgXML + "</" + MsgXMLArray[0][0][0][0] + ">\n";          // Imprime a Tag de Nivel 0 de Fim
		
		return(MsgXML);
		
	}// Fim do Método
	
}
