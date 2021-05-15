package com.siteapprfs.main.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.siteapprfs.main.services.Servs;

@RestController
@RequestMapping("/file")
public class FileController {
	
	 public static String getFileType(byte[] fileData) {
		 String rs = "";
		 List<Byte> arr = new ArrayList<>() ;
		 
		 
		 for(byte bt: fileData) {
			 arr.add(bt);
		 }
		 
//		 /*
//		 Optional<Object>  obj =  arr.parallelStream()
//				 .filter(p -> verificaP(p))
//				 .map(p -> p.toString())
//				 .findAny();
//		 */
//		 if(obj.isPresent()) {
//			 rs = obj.get().toString();
//			 System.out.print("Finded : "+rs);
//		 }
		 
	
         String type = "undefined";
         
         int v1 = Byte.toUnsignedInt(fileData[0]);
         int v2 = Byte.toUnsignedInt(fileData[1]);
         int v3 = Byte.toUnsignedInt(fileData[2]);
         int v4 = Byte.toUnsignedInt(fileData[3]);
         int v5 = Byte.toUnsignedInt(fileData[4]);
         
         
         
         char v1c = (char)v1;
         char v2c = (char)v2;
         char v3c = (char)v3;
         char v4c = (char)v4;
         char v5c = (char)v5;
         
         
         final char[] charArray = { v1c, v2c, v3c,v4c, v5c };
         
         String val = new String(charArray);
         String val2 =  new String(fileData);
         
         
         
         
         
         if(v1  == 0x89 && v2 == 0x50)
             type = "png";
         else if(v1 == 0xFF && v2 == 0xD8)
             type = "jpg";
         else if(v1 == 0x25 && v2 == 0x50)
             type = "pdf";

        return rs;
    }

	public static boolean verificaP(Object p) {
		return p.toString().equals("JFIF");
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/upload")
	public String index(MultipartFile file) throws IOException {
		ContentInfoUtil util = new ContentInfoUtil();
		
		ContentInfo info = util.findMatch(file.getBytes());
		
		String fs = info.getMimeType();
		
		//String contentType = file.getContentType();
		//String fs = getFileType(file.getBytes());
		
		//System.out.println("Arquivo: "+fs);
		
		return "encontrado: "+fs;
	}

}
