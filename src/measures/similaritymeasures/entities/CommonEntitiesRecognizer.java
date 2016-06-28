/**
 * @author Hernani Costa iCorpora EXPERT (EXPloiting Empirical appRoaches to Translation) ESR3 - Collection & preparation of multilingual
 *         data for multiple corpus-based approaches to translation Department of Translation and Interpreting Faculty of Philosophy and
 *         Humanities
 *
 *         Copyright (c) 2013-2016 University of Malaga, Spain. All rights reserved.
 */
package measures.similaritymeasures.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nlp.stopwords.MyStopwordChecker;
import nlp.tokenizer.MyTokenizer;
import constantsfilesfolders.Constants;
import constantsfilesfolders.PublicFunctions;

/**
 * Identifies common entities/tokens/words between two lists.
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2014
 */
public class CommonEntitiesRecognizer
{
	private MyStopwordChecker myStopwordChecker = null;
	private List<String> stopwords = null;

	/**
	 * Default constructor. The language is used to load the associated stopword list.
	 * 
	 * @param language
	 *           language
	 */
	public CommonEntitiesRecognizer(String language)
	{
		// TODO Right now only stopword list for portuguese, english and spanish are available. Add stopword lists for RU, IT, FR,
		// DE
		myStopwordChecker = new MyStopwordChecker(language);
		stopwords = myStopwordChecker.getStopwords();
	}

	/**
	 * Returns all the common entities {@link CommonEntities} between two texts.
	 * 
	 * @param text1
	 *           - text
	 * @param text2
	 *           - text
	 * @param consideringStopwords
	 *           - true if considering stopwords, otherwise false
	 * @return {@link CommonEntities}
	 */
	public CommonEntities getCommonEntities(List<String> text1, List<String> text2, boolean consideringStopwords)
	{
		List<String> uniqueTokensText1 = new ArrayList<String>();
		String tokenText1 = "";
		String _tempToken = "";

		/**
		 * Filtering duplicated tokens in the text1.
		 */
		for (int i = 0; i < text1.size(); i++)
		{
			tokenText1 = text1.get(i);
			//System.out.println("t1:" + tokenText1);
			_tempToken = PublicFunctions.removeSpecialChars(tokenText1);

			if (_tempToken.length() < 3)
			{
				//System.err.println("<3: " + _tempToken);
				continue;
			}

			if (consideringStopwords) if (stopwords.contains(_tempToken)) continue;

			if (!uniqueTokensText1.contains(_tempToken)) uniqueTokensText1.add(_tempToken);
		}

		//System.out.println(uniqueTokensText1.toString());
		/**
		 * Comparing the tokens identified in the text1 with the tokens in the text2.
		 */
		List<String> commonTokens = new ArrayList<String>();
		String tokenText2 = "";
		for (int j = 0; j < text2.size(); j++)
		{
			tokenText2 = text2.get(j);

			if (uniqueTokensText1.contains(tokenText2))
			{
				//System.out.println(tokenText2);
				if (!commonTokens.contains(tokenText2))
				{
					//System.out.println("adding: "+tokenText2);
					commonTokens.add(tokenText2);
				}
			}

		}

		/**
		 * Creating a list with common entities containing their frequency in both texts (text1 and text2).
		 */
		CommonEntities commonEntities = new CommonEntities();
		Entity newEntity = null;
		for (String token : commonTokens)
		{
			//System.out.println(">> creating entity:" + token);
			newEntity = new Entity(token, Collections.frequency(text1, token), Collections.frequency(text2, token));
			commonEntities.addEntity(newEntity);
		}

		return commonEntities;
	}

	/**
	 * ================== For testing purposes ==================
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		String rawTextES1 = "spa hotel sercotel valle del este enjoy the spa in our hotel in vera spa facilitiesfacialbodymassageshydrotherapyhands and feetaromatherapyprog.1 dayprog.2 , daysprog.3 , dayswaxing about spa facilities the spa at the sercotel valle del este , an innovative and exclusive health and sports facility , is a true wellness centre located in the heart of the valle del este golf resort . , a centre highly specialised in hydrotherapy , fitness , beauty and health . , the spa valle del este offers a sanctuary where you can enjoy an environment full of sensations . , we will help you achieve your health and wellbeing goals . , learn to relax and enjoy yourself . , its modern architecture blends in perfectly with the wonderful surroundings . , the spa valle del este has an exceptional team of professionals at your service who will make you feel special . , reservations : tel . , 950 548 600 fax : 950 461 006 spa@valledeleste .es , opening hours : open on monday from 15:30 h to 21:00 h tuesday to friday : 15:30 h to 21h saturdays from 10:00 h to 19:00 h sundays : 10am to 7pm ads not by this site information of interest • the water used at the spa is treated with bromine . , • according to security policies , access to the spa is limited to people older than 14 . • according to security policies , access to spa treatments and massages is limited to people older than 18 . • access to the spa hydrothermal area includes : sensations shower , sauna , turkish bath , ice cyclonic shower , soothing waterfall , gravel path , 42° jacuzzi , cold pool of 15° water , 33° jacuzzi , 4 relaxation beds , outdoor solarium , outdoor heated pool , relaxation area , energizing cascade , dynamic pool with a neck jet , nozzles , lumbar jet , gooseneck and bubble bath . , • all the treatments are personalised and guided by physiotherapists . , • for massage vouchers , please ask at the spa’s reception . , • our towel service at the valle del este spa , provided by the reception and required for the wet area and the gym , will have an additional charge of 1€ per towel . , • the use of a cap is mandatory for the hydro-thermal circuit . , if you don’t have a cap , you may buy one at our spa for 1,50 € . , spa hydrotherapywet area with the following features : gooseneck waterfall , bubble jets , whirlpool , pebble path , cyclonic shower , jet shower , shower sensations , cold water pool , sauna , turkish bath , ice and solarium overlooking the golf course . , achieve your wellbeing through the power of water ; enjoy its benefits in any of its forms . , our staff will recommend the best personalized treatment for you to regain balance , revitalise or simply relax . , price from :10.00€ , vat incl . , solariumoutdoor relax area , to enjoy the sun and spectacular views of the golf course . , wellness roomspa valle del este offers a fitness area with the latest , most sophisticated equipment and a large selection of weight and cardiovascular machines that adapt to the needs of each user . , treatment cabinsput yourself in our hands and forget everything else . , we are professionals and committed to providing the best services to obtain the best results . , treatment cabins , massages , beauty and physiotherapy . , thalaform treatment tubs , everything you need to achieve and improve the desired results . , about facial spa wellness treatment for men price from :45.00€ , vat incl . , facial hygiene price from :45.00€ , vat incl . , intensive hydration treatment price from :39.00€ , vat incl . , sensitive skin treatment price from :39.00€ , vat incl . , anti-wrinkles treatment price from :39.00€ , vat incl . , specific treatment for eyes price from :39.00€ , vat incl . , eyebrow or eyelash dye price from :14.00€ , vat incl . , about body chocotherapy , chocolate treatment( peeling , wrap and hydration ) price from :51.00€ , vat incl . , coffee therapy , coffee treatment( peeling , wrap and hydration ) price from :51.00€ , vat incl . , cappuccino therapycoffee and chocolate treatment ( peeling , wrap and hydration ) price from :51.00€ , vat incl . , winetherapyred grape treatment ( peeling , wrap , hydro-massage and hydration ) price from :63.00€ , vat incl . , spa exfoliating cocoascrub( cocoa based skin exfoliation and regeneration ) price from :22.00€ , vat incl . , spa body peeling( exfoliation and skin regenerator ) price from :22.00€ , vat incl . , chocolate wrap( hydrating , nutritive , smoothing and anti-cellulite ) price from :42.00€ , vat incl . , red grape wrap(anti-oxidant , circulatory ) price from :42.00€ , vat incl . , coffee wrap( draining , detoxing , slimming ) price from :42.00€ , vat incl . , cappuccino wrap(anti-cellulite , draining and slimming ) price from :42.00€ , vat incl . , cryogenic wrap for legs(anti-inflammatory and circulation activator ) price from :42.00€ , vat incl . , firming body wrap( base don algae , detoxing and firming effects ) price from :42.00€ , vat incl . , marine-autoheating mud on backtherapeutic mud with relaxing , calming and anti-rheumatic effects . , price from :42.00€ , vat incl . , about massages manual lymphatic drainage ( 25m . ) , price from :28.00€ , vat incl . , therapeutic or de-contracting massage ( 25-50m . ) , price from :28.00€ , vat incl . , relaxing massage ( 25-50m . ) , price from :28.00€ , vat incl . , facial massage price from :24.00€ , vat incl . , foot massage price from :24.00€ , vat incl . , about hydrotherapy red grape hydro-massagein thalaform tub ( rejuvenating and activates circulation ) *thalaform : bubble bath with programmed massage from feet up to the neck , with a relaxing and de-contracting effect . , price from :18.00€ , vat incl . , relaxing hydro-massagein thalaform tub (vanilla or lavender ) *thalaform : bubble bath with programmed massage from feet up to the neck , with a relaxing and de-contracting effect . , price from :18.00€ , vat incl . , marine hydro-massagein thalaform tub ( revitalising and re-mineralising ) *thalaform : bubble bath with programmed massage from feet up to the neck , with a relaxing and de-contracting effect . , price from :18.00€ , vat incl . , revitalizing hydro-massagein thalaform tub ( roses ) *thalaform : bubble bath with programmed massage from feet up to the neck , with a relaxing and de-contracting effect . , price from :18.00€ , vat incl . , energizing hydro-massagein thalaform tub ( eucalyptus ) *thalaform : bubble bath with programmed massage from feet up to the neck , with a relaxing and decontracting effect . , price from :18.00€ , vat incl . , jet shower – kneipp shower(toning , vaso - dilatador-constrictor ) price from :8.00€ , vat incl . , about hands and feet varnishing / french varnish price from :10.00€ , vat incl . , standard manicure / french manicure price from :21.00€ , vat incl . , hand treatment( peeling , manicure , paraffin ) price from :27.00€ , vat incl . , standard pedicure price from :27.00€ , vat incl . , foot treatment( peeling , pedicure , paraffin ) price from :31.00€ , vat incl . , paraffin wrap for hands and feet price from :14.00€ , vat incl . , hydration for hands and feet( peeling , paraffin wrap and hydration ) price from :19.00€ , vat incl . , about aromatherapy aromatherapy is the therapeutic use of essential oils . , therapy with essential oils , highly aromatic , has positive and natural effects on physical and mental health . , sensual , emotional and spiritual , each aromatherapy treatment sends a message of purity and interior peace , coming from the depths of nature . , it is a fountain of sensations , harmony and light , a space to free your own essence . , several essences to choose from . , relaxation massage ( 25-50 ' ) price from :30.00€ , vat incl . , facial massage price from :27.00€ , vat incl . , about prog.1 day aquatherapy - marinejet shower- kneipp shower ( toning , vasodilator-constrictor ) . , marine hydro-massage in thalaform bathtub ( revitalizing and re-mineralising ) . , marine essences body wrap ( re-mineralising and detoxing ) . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :67.00€ , vat incl . , beautyspa body peeling ( exfoliating and regenerating ) . , marine essences body wrap ( re-mineralising and detoxing ) . , facial treatment ( specific according to skin type ) . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :92.00€ , vat incl . , special for sportspeoplejet shower- kneipp shower ( toning , vasodilator-constrictor ) . , marine hydro-massage in thalaform bathtub ( revitalizing and re-mineralising ) . , cryogenic wrap for legs ( anti-inflammatory and activates circulation ) . , de-contructing massage ( back , arms or legs ) . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :82.00€ , vat incl . , special for executivesjet shower- kneipp shower ( toning , vasodilator-constrictor ) . , relax hydro-massage in thalaform bathtub ( relaxing and re-mineralising ) . , marine-autoheating mud on back ( therapeutic mud with relaxing , calming and anti-rheumatic effects ) . , foot massage therapeutic back massage . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :95.00€ , vat incl . , relaxrelax hydro-massage in thalaform bathtub ( relaxing and re-mineralising ) . , facial massage . , relaxing body massage . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :54.00€ , vat incl . , about prog.2 days beautyday 1 spa body peeling ( exfoliating and rejuvenating ) . , marine hydro-massage in thalaform bathtub ( revitalizing and re-mineralising ) . , facial treatment ( specific according to each type of skin ) . , day 2 marine essences body wrap ( re-mineralising and detoxing ) . , specific treatment for eyes . , hydration for hands and feet ( peeling , massage and paraffin wrap ) . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :148.00€ , vat incl . , golfday 1 jet shower- kneipp shower ( toning , vasodilator-constrictor ) . , marine essences body wrap ( re-mineralising and detoxing ) . , therapeutic massage ( back ) . , day 2 cryogenic wrap on legs ( anti-inflammatory and activates circulation ) . , drainage massage ( legs ) . , foot massage spa* ( hydro-thermal circuit according to specific needs ) . , price from :118.00€ , vat incl . , anti-stressday 1 relax hydro-massage in thalaform bathtub ( relaxing and re-mineralising ) . , relaxing massage ( back ) . , foot massage day 2 jet shower- kneipp shower ( toning , vasodilator-constrictor ) . , facial massage . , relaxing body massage . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :90.00€ , vat incl . , about prog.3 days beauty and health day 1 spa body peeling ( exfoliating and rejuvenating ) . , marine hydro-massage in thalaform bathtub ( revitalizing and re-mineralising ) . , therapeutic massage ( back ) . , day 2 jet shower- kneipp shower ( toning , vasodilator-constrictor ) . , marine essences body wrap ( re-mineralising and detoxing ) . , facial treatment ( specific according to each type of skin ) . , day 3 marine-autoheating mud on back ( therapeutic mud with relaxing , calming and anti-rheumatic functions ) . , foot massage specific eye treatment . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :212.00€ , vat incl . , new momday 1 spa body peeling ( exfoliating and regenerating ) reafirming wrap ( based on algae , firming and re-modelling effect ) . , de-contracting massage ( back ) . , day 2 jet shower - kneipp shower ( toning , vasodilator-constrictor ) . , cryogenic wrap on legs ( anti-inflammatory and activates circulation ) . , lymphatic drainage ( legs ) . , day 3 desintoxicating body wrap ( with algae , detoxing , draining and lipo-reducing effects ) . , seaweed hydro-massage in thalaform bathtub ( slimming , detoxing and refirming ) . , relaxing body massage . , spa* ( hydrothermal circuit designed according to needs ) . , price from :216.00€ , vat incl . , welbeing and relaxday 1 relax hydro-massage in thalaform bathtub ( relaxing and re-mineralising ) . , relaxing massage ( back ) . , foot massage . , day 2 jet shower - kneipp shower ( toning , vasodilator-constrictor ) wellness spa treatment for men . , relaxing body massage . , day 3 marine hydro-massage in thalaform bathtub ( revitalizing and re-mineralising ) facial massage marine-autoheating mud on back . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :173.00€ , vat incl . , sweet , sour and fruityday 1 : chocotherapy ( anti-cellulite ) spa cocoa scrub (skin exfoliating and regenerating ) . , chocolate wrap ( hydrating , nutritive , smoothing and anti-cellulite ) . , hydrating with karité butter and masotherapy . , day 2 : coffee therapy ( draining and slimming ) coffee wrap ( draining , detoxing , slimming ) . , lymphatic manual drainage ( legs or abdomen ) . , hydrating with karité butter and masotherapy . , day 3 : winetherapy ( circulatory , anti-oxidant ) red grape hydro-massage in thalaform tub ( rejuvenating and circulatory ) . , red grape wrap ( antioxidant , circulatory ) . , hydrating with grape seed extract and masotherapy . , spa* ( hydro-thermal circuit according to specific needs ) . , price from :169.00€ , vat incl . , about waxing whole leg price from :18.00€ , vat incl . , half leg or thigh price from :14.00€ , vat incl . , bikini or underarm price from :14.00€ , vat incl . , brazilian bikini price from :16.00€ , vat incl . , upper lip , chin or eyebrows price from :9.00€ , vat incl . , arms price from :18.00€ , vat incl . , back or chest price from :22.00€ , vat incl . ";
		String rawTextES2 = "welcome situated in the privileged fairplay golf hotel & spa the 2000m² spa is found in benlaup casas viejas , between typical white villages viejer de la frontera , and medina sidonia and only around 1 hour from cadiz city and 45 minutes from jerez . , benalup golf & country club is onsite if you have time to squeeze a round of 18 holes before or after relaxing in our wonderful spa ! having fulfilled the demanding criteria for being chosen for the fourth time , we are confident that we deliver a wonderful product to our guests and offer the highest standard of luxury and hospitality . , fairplay spa is an established seal of quality when it comes to asses luxury spas with regard to their professionalism , standard service and quality . , spa installations treatments area * 7 treatment rooms * 1 vichy shower * 1 jet shower * 2 niagara baths zona spa * therapeutic aqua medic pool with beds with bubbles , tropical shower , waterfall , jets with underwater massage , geysers and central jacuzzi * 2 separate jacuzzi ( one hot and one cold ) * sauna * hamman * relax room with outdoor terrace and views over the valley * packages 1 day duration precio basic 70 min 95 € relax 120 min 135 € anti-stress 180 min 190 € * 2 days duration precio fairplay escape 165 min 200 € orient escape 120 min 200 € deluxe escape 205 min 250 € * 3 days duration precio slimming anti-cellulite intensive treatment 195 min 295 € * 5 days duration precio gran fairplay 375 min 495 € circuit the aquamedic pool the fairplay therapy pool is the jewel of the spa . , effortlessly exercise your way through different hydromassage stations using the pool´s therapeutic jet streams , micro – bubbles and geysers to rehabilitate injured muscles , to relieve stress and for relaxation . , note : the aquamédic pool is designed for therapeutic exercise and access is not permited for children under the age of 13 . , spa circuit experience a circuit of our leading spa enjoying 2000m2 of the best installations : * aquamedic pool , hamman , jacuzzi , sauna * fruit drinks and water at our vitamin bar * towel and locker services * indoor parking duration : 120 min aesthetics waxing precio chin 6 € upper lip 6 € armpits 12 € eyebrows 12 € bikini line 12 € arms 25 € half legs 25 € bikini 25 € back 30 € chest 30 € full legs 30 € manicure and pedicure duration precio hands treatment 70 min 25 € foot treatment 70 min 30 € manicure 50 min 45 € pedicure 50 min 50 € hydrotherapy hydrotherapy duration precio jet shower 15 min 25 € hydrobath niagara 20 min 30 € massage massage duration precio happy feet 20 min 30 € silky hands 20 min 30 € peace of the senses ( relax local ) 20 min 40 € fairplay ( intensive local ) 25 min 50 € anti-cellulite 30 min 60 € peace of the senses ( relax general ) 40 min 60 € fairplay ( intensive general ) 40 min 70 € limphatic drainage 55 min 75 € sensations 55 min 75 € orient sensations 40 min 80 € vichy shower 40 min 80 € treatments facial duration precio face , neck and head 20 min 35 € facial express 30 min 45 € eye contour 40 min 50 € specific facial 50 min 60 € peeling 55 min 65 € specific gentleman with hot towels 50 min 70 € intensive hydration 75 min 80 € antiage 80 min 90 € face and body 85 min 95 € body scrub and wrapping duration precio body scrub with sesalts and olive oils 20 min 40 € body wrapping 20 min 40 € body scrub ocoa or green tea 20 min 40 € body scrub with orange blosson and cedarwood 25 min 50 € body scrub with cinnamon and giner 25 min 50 € body scrub with lemon and petitgrain 25 min 50 € body scrub with vanilla and sandalwood 25 min 50 € siluet duration precio siluet 55 min 85 € slimming 80 min 90 € sensation duration precio orient sensations ceremony 60 min 110 € body scrub & massage with cherry blossom and lotus 80 min 110 € hairdressing exclusive services precio bride semi hair up 50 € hair up 60 € hair services precio colour ( each 30gr ) 8 € colour ( each 60gr ) 15 € wash only ( shampoo + hair conditioner or mask ) 15 € high lights 25 € blow and dry 25 € cut and definition + wash only 30 € cut and definition + blow and dry 40 € just for him precio definition ( beard , moustache and neck ) 10 € cut and style 15 € intensive hair care precio hair intensive treatment 15 € ";
		ArrayList<String> tokensSentence1 = null;
		ArrayList<String> tokensSentence2 = null;
		MyTokenizer tokenizer = null;

		tokenizer = new MyTokenizer(Constants.EN);

		tokensSentence1 = (ArrayList<String>) tokenizer.getTokenisedSentenceList(rawTextES1);
		tokensSentence2 = (ArrayList<String>) tokenizer.getTokenisedSentenceList(rawTextES2);

		CommonEntitiesRecognizer recognizer = new CommonEntitiesRecognizer(Constants.EN);
		CommonEntities commonentities = recognizer.getCommonEntities(tokensSentence1, tokensSentence2, true);
		System.out.println(commonentities.toString());

	}
}
