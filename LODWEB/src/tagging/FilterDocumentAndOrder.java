package tagging;

import cosinesimilarity.LuceneCosineSimilarity;
import util.StringUtilsNode;

public class FilterDocumentAndOrder {
	public static void main(String[] args) {

		/* Variaveis para teste */
		String a = "Brazil (/brəˈzɪl/; Portuguese: Brasil [bɾaˈziw] ), officially the Federative Republic of Brazil (Portuguese: República Federativa do Brasil, About this sound listen ) is the largest sovereign state in Latin America. A federal republic, Brazil is the world's fifth-largest country, by both geographical area and total population. It is the largest Portuguese-speaking country in the world, and the only one in the Americas. Bounded by the Atlantic Ocean on the east, Brazil has a coastline of 7,491 km (4,655 mi). It borders all other South American countries except Ecuador and Chile and occupies 47.3 percent of the continent of South America. Its Amazon River basin includes a vast tropical forest, home to diverse wildlife, a variety of ecological systems, and extensive natural resources spanning numerous protected habitats. This unique environmental heritage makes Brazil one of 17 megadiverse countries, and is the subject of significant global interest and debate regarding deforestation and environmental protection. Brazil was inhabited by numerous tribal nations prior to the landing in 1500 of explorer Pedro Álvares Cabral, who claimed the area for the Portuguese Empire. Brazil remained a Portuguese colony until 1808, when the capital of the empire was transferred from Lisbon to Rio de Janeiro. In 1815, the colony was elevated to the rank of kingdom upon the formation of the United Kingdom of Portugal, Brazil and the Algarves. Independence was achieved in 1822 with the creation of the Empire of Brazil, a unitary state governed under a constitutional monarchy and a parliamentary system. The ratification of the first constitution in 1824 led to the formation of a bicameral legislature, now called the National Congress. The country became a presidential republic in 1889 following a military coup d'état. An authoritarian military junta came to power in 1964 and ruled until 1985, after which civilian governance resumed. Brazil's current constitution, formulated in 1988, defines it as a federal republic. The federation is composed of the union of the Federal District, the 26 states, and the 5,570 municipalities. Brazil has the world's highest population of Roman Catholics and is the world's most populous Catholic-majority country. Brazil's economy is the world's ninth-largest by nominal GDP and seventh-largest by GDP (PPP) as of 2015. A member of the BRICS group, Brazil until 2010 had one of the world's fastest growing major economies, with its economic reforms giving the country new international recognition and influence. Brazil's national development bank plays an important role for the country's economic growth. Brazil is a founding member of the United Nations, the G20, BRICS, Unasul, Mercosul, Organization of American States, Organization of Ibero-American States, CPLP and the Latin Union. Brazil is a regional power in Latin America and a middle power in international affairs, with some analysts identifying it as an emerging global power. One of the world's major breadbaskets, Brazil has been the largest producer of coffee for the last 150 years.";
		String b = "Samba is a Brazilian musical genre and dance style originating in Brazil, with its roots in Africa via the West African slave trade and African religious traditions, particularly Angola and the Congo. Although there were various forms of samba in Brazil in the form of various popular rhythms and regional dances that originated from the drumming, samba as music genre is seen as a musical expression of urban Rio de Janeiro, then the capital of Imperial Brazil. It is recognized around the world as a symbol of Brazil and the Brazilian Carnival. Considered one of the most popular Brazilian cultural expressions, samba has become an icon of Brazilian national identity.The Bahian Samba de Roda (dance circle), which became a UNESCO Heritage of Humanity in 2005, is the main root of the samba carioca, the samba that is played and danced in Rio de Janeiro. The modern samba that emerged at the beginning of the 20th century is predominantly in a 2/4 tempo varied with the conscious use of a sung chorus to a batucada rhythm, with various stanzas of declaratory verses. Traditionally, the samba is played by strings (cavaquinho and various types of guitar) and various percussion instruments such as tamborim. Influenced by American orchestras in vogue since the Second World War and the cultural impact of US music post-war, samba began to use trombones, trumpets, choros, flutes, and clarinets.[citation needed] In addition to distinct rhythms and meters, samba brings a whole historical culture of food, varied dances (miudinho, coco, samba de roda, and pernada), parties, clothes such as linen shirts, and the Naif painting of established names such as Nelson Sargento, Guilherme de Brito, and Heitor dos Prazeres. Anonymous community artists, including painters, sculptors, designers, and stylists, make the clothes, costumes, carnival floats, and cars, opening the doors of schools of samba. There is also a great tradition of ballroom samba in Brazil, with many styles. Samba de Gafieira is the style more famous in Rio de Janeiro, where common people used to go to the gafieira parties since the 1930s, and where the moves and identity of this dance has emerged, getting more and more different from its African, European, Argentinian and Cuban origins and influences. The Samba National Day is celebrated on December 2. The date was established at the initiative of Luis Monteiro da Costa, an Alderman of Salvador, in honor of Ary Barroso. He composed even though he had never been in Bahia. Thus 2 December marked the first visit of Ary Barroso to Salvador. Initially, this day was celebrated only in Salvador, but eventually it turned into a national holiday. Samba is a local style in Southeastern Brazil and Northeast Brazil, especially in Rio de Janeiro, São Paulo, Salvador and Recife. Its importance as Brazil's national music transcends region, however; samba schools, samba musicians and carnival organizations centered around the performance of samba exist in every region of the country, even though other musical styles prevail in various regions (for instance, in Southern Brazil, Center-West Brazil, and all of the Brazilian countryside, Sertanejo, or Brazilian country music, is the most popular style). Since Rio de Janeiro is the most popular Brazilian city worldwide, usually samba is used to identify Brazilians as part of the same national culture.";

		/* Duas Funções de retirar caracteres especiais */
		System.out.println("Função retirando caracteres especial - PATRICK");
		System.out.println(StringUtilsNode.removeSpecialCharacters(a));
		System.out.println("------------------------------------------");
		System.out.println("Função retirando caracteres especial - LODWEB");
		System.out.println(StringUtilsNode.removeInvalidCharacteres(a));
		System.out.println("------------------------------------------");

		System.out.println("Calculo sem RETIRAR caractere especiais");
		System.out.println(LuceneCosineSimilarity.getCosineSimilarity(a, b));
		System.out.println("------------------------------------------");

		System.out.println("UTILIZANDO FUNÇÃO removeSpecialCharacters() - PATRICK");
		System.out.println(LuceneCosineSimilarity.getCosineSimilarity(StringUtilsNode.removeSpecialCharacters(a), b));
		System.out.println("------------------------------------------");

		System.out.println("UTILIZANDO FUNÇÃO removeInvalidCharacteres() - LODWEB");
		System.out.println(LuceneCosineSimilarity.getCosineSimilarity(StringUtilsNode.removeInvalidCharacteres(a), b));
		System.out.println("------------------------------------------");

		System.out.println("Função retirando paralvra selecionas - PATRICK");
		System.out.println(StringUtilsNode.removeWords(a));

		System.out.println("UTILIZANDO FUNÇÃO removeWords() - PATRICK");
		System.out.println(LuceneCosineSimilarity.getCosineSimilarity(
				StringUtilsNode.removeInvalidCharacteres(StringUtilsNode.removeWords(a)),
				StringUtilsNode.removeWords(b)));
		System.out.println("------------------------------------------");

	}
}
