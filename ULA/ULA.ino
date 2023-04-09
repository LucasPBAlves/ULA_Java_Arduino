//declaração das portas de cada led
int led1 = 10;
int led2 = 11;
int led3 = 12;
int led4 = 13;

//Variáveis da String a ser passada
String Cod_Hex;
int Tamanho_Str;

void setup() {
  Serial.begin(9600);     
	pinMode(led1,OUTPUT);
  pinMode(led2,OUTPUT);
  pinMode(led3,OUTPUT);
  pinMode(led4,OUTPUT);
  Serial.println("Digite a String Hexadecimal: "); 

}

void loop() {

  //Testa se tem valores a receber
  if(Serial.available() > 0){
    
    Cod_Hex= Serial.readString();//Lê String
    Tamanho_Str = Cod_Hex.length() + 1;//Define tamanho da String

    for (int i = 0; i < Tamanho_Str; i += 4) {//For para definir a operação de cada parte da String
      //Transforma a String em Blocos de 3 Valores
      String cod = (Cod_Hex.substring(i, i + 3));
      char x = cod.charAt(0);
      char y = cod.charAt(1);
      char s = cod.charAt(2);

      byte a = convert(x);
      byte b = convert(y);
      byte bin[4];

      //Switch
      switch (s) {
      case '0':{//~A (negado)

        getBinary(~a, bin);
       
        break;
      }
      case '1':{//(A or B)'

        getBinary(~(a | b), bin);

        break;
      }
      case '2':{//A'B
        
        getBinary(~a & b, bin);

        break;
      }
      case '3':{//0 lógico

        getBinary(0, bin);

        break;
      }
      case '4':{//(AB)'

        getBinary(~(a & b), bin);

        break;
      }
      case '5':{//B'

        getBinary(~b, bin);

        break;
      }
      case '6':{//A xor B

        getBinary(a^b, bin);

        break;
      }
      case '7':{//A&B'

        getBinary(a & ~b, bin);

        break;
      }
      case '8':{//A' or B

        getBinary(~a | b, bin);

        break;
      }
      case '9':{//(A xor B)'

        getBinary(~(a ^ b), bin);

        break;
      }
      case 'A':{//B

        getBinary(b, bin);

        break;
      }
      case 'B':{//AB

        getBinary(a & b, bin);

        break;
      }
      case 'C':{//1 lógico

        getBinary(15, bin);

        break;
      }
      case 'D':{//A or B'

        getBinary(a | ~b, bin);

        break;
      }
      case 'E':{//A or B

        getBinary(a | b, bin);

        break;
      }
      case 'F':{//A

        getBinary(a, bin);

        break;
      }
      
      }//end switch

      //Define os LEDS      
      digitalWrite(led1, bin[0]);
      digitalWrite(led2, bin[1]);
      digitalWrite(led3, bin[2]);
      digitalWrite(led4, bin[3]);
      
      delay(2000);
    }//end for
  }//end if
}//end loop

//funcão para converter HEXADECIMAL em INT
byte convert(char a){
   switch (a) {
      case '0': return 0;
      case '1': return 1;
      case '2': return 2;
      case '3': return 3;
      case '4': return 4;
      case '5': return 5;
      case '6': return 6;
      case '7': return 7;
      case '8': return 8;
      case '9': return 9;
      case 'A': return 10;
      case 'B': return 11;
      case 'C': return 12;
      case 'D': return 13;
      case 'E': return 14;
      case 'F': return 15;
      }

 }

 void getBinary(byte b, byte* buf) {
  for (int i = 3; i >= 0; i-- ) {
    buf[i] = (b >> i) & 0X01; //shift and select first bit
  }
}