package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;

import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CompilerImpl implements Compiler {

	
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static final Struct boolTyp = new Struct(Struct.Bool);
	

	public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
		
		Logger log = Logger.getLogger(CompilerImpl.class);
		
		Reader br = null;
		try {
			File sourceCode = new File(sourceFilePath);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			log.info("=================================== Lexer ===================================");
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  
	        
	        log.info("=================================== Parser ===================================");
	        
	        Program prog = (Program)(s.value);
	        Tab.init();
	        
	        //dodavanje bool tipa u tabelu simbola
	        Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolTyp));
	        
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			
			log.info("=================================== Semantic ===================================");
			
			// ispis prepoznatih programskih konstrukcija
			SemanticAnalyzer v = new SemanticAnalyzer(boolTyp);
			prog.traverseBottomUp(v); 
	      
			log.info(" Print count calls = " + v.printCallCount);

			log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
			
			Tab.dump();
			
			if(!p.errorDetected && v.passed() && !CompilerErrorList.lexerError){
				log.info("Parsiranje uspesno zavrseno!");
				File objFile = new File(outputFilePath);
				if(objFile.exists()) {
					objFile.delete();
				}
				CodeGenerator codeGenerator = new CodeGenerator(boolTyp);
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		return CompilerErrorList.cmpErrorList;
	}

	public static void main(String[] args) {
		
		CompilerImpl comImpl = new CompilerImpl();
		List<CompilerError> list = comImpl.compile("test/program.mj", "test/program.obj");
		
		System.out.println("GRESKE::");
		if(!list.isEmpty()) {
			for (CompilerError compilerError : list) {
				System.out.println(compilerError.toString());
			}
		}else {
			System.out.println("Nema!");
		}
	}

}
