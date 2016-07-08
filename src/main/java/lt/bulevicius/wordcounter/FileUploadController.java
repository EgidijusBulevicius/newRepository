package lt.bulevicius.wordcounter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	
	//Is prisegtu failu nuskaitomi zodziai ir jie sudedami i hasmap
	protected void putWordToMap(Map<String, Integer> wordsToCounter, MultipartFile[] files) throws IOException {
		
		for (int i = 0; i < files.length; i++) {
			if(files[i].getSize() == 0) {
				continue;
			}
			
			byte[] bytes = files[i].getBytes();
			List<String> list = Arrays.asList(new String(bytes).split(" "));
			
			for(String word : list) {
				Integer counter = wordsToCounter.get(word);
				if(counter == null) {
					wordsToCounter.put(word, 1);
				} else {
					wordsToCounter.put(word, counter + 1);
				}
			}
		}
	}
	
	//Nuskaityti zodziai is hasmap atrenkami pagal pirmaja zodzio raide
	protected void writeDublicateWordsToFourFiles(Set<Entry<String, Integer>> words, FileWriter ag, FileWriter hn, FileWriter ou, FileWriter vz) throws IOException {

		for(Entry<String, Integer> entry : words) {
			String word = entry.getKey();
			Integer counter = entry.getValue();	
			int ascii = (int) word.charAt(0);
			if(ascii >= 65 && ascii <= 71 || ascii >= 97 && ascii <= 103){
				ag.write(word+ " " + counter + "\n");
			}
			
			if(ascii >= 72 && ascii <= 78 || ascii >= 104 && ascii <= 110){
				hn.write(word+ " " + counter + "\n");
			}
			
			if(ascii >= 79 && ascii <= 85 || ascii >= 111 && ascii <= 117){
				ou.write(word+ " " + counter + "\n");
			}
			
			if(ascii >= 86 && ascii <= 90 || ascii >= 118 && ascii <= 122){
				vz.write(word+ " " + counter + "\n");
			}	
		}
	}

	//Nauju failu kurimas ir atrinkti zodziai su savo pasikartojamumu surasomi i failus
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files) throws IOException {
		
		Map<String, Integer> wordsToCounter = new HashMap<String, Integer>();
		putWordToMap(wordsToCounter, files);
		
		FileWriter writerAG = new FileWriter("fileA-G.txt");
		FileWriter writerHN = new FileWriter("fileH-N.txt");
		FileWriter writerOU = new FileWriter("fileO-U.txt");
		FileWriter writerVZ = new FileWriter("fileV-Z.txt");
		
		Set<Entry<String, Integer>> words = wordsToCounter.entrySet();
		writeDublicateWordsToFourFiles(words, writerAG, writerHN, writerOU, writerVZ);
		return "Prisegti failai sekmingai nuskaityti ir juose esantys pasikartojantys zodziai surasyti i tekstinius failus pagal pirmaja zodziu raide!";
	}
	
}