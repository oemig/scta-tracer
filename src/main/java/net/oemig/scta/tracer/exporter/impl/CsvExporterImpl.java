package net.oemig.scta.tracer.exporter.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFileChooser;

import net.oemig.scta.tracer.configuration.IConfiguration;
import net.oemig.scta.tracer.exporter.IExporter;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.Participant;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.ResponseData;

import com.google.common.collect.Maps;

public final class CsvExporterImpl implements IExporter {

	public static CsvExporterImpl with(IConfiguration aConfiguration){
		return new CsvExporterImpl(aConfiguration);
	}

	private IConfiguration configuration;
	
	private CsvExporterImpl(IConfiguration aConfiguration){
		configuration=aConfiguration;
	}
	
	
	@Override
	public void export(ITraceModel aModel) {
		
		
		try {
			JFileChooser fc=new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			int state=fc.showSaveDialog(null);
			if(JFileChooser.APPROVE_OPTION==state){
				
			
			FileWriter fw = new FileWriter(fc.getSelectedFile());
			
			for(Session s:aModel.getCurrentTrace().getSession()){
				for (Run r:s.getRun()){
					Map<String, Participant> pMap=Maps.newHashMap();
					for(Participant p: r.getParticipant()){
						pMap.put(p.getName(), p);
					}
					
					for(ResponseData rd:r.getResponseData()){
						writeLine(fw, aModel.getCurrentTrace().getName(),
											s.getName(),
											"runname",
											rd.getParticipant(), 
											pMap.get(rd.getParticipant()).getExperimentId(),
											rd.isCorrect(), 
											rd.getResponsetime(),
											rd.getQuestionType().toString());
					}
				}
			}
			fw.flush();
			fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
	}
	
	private void writeLine(FileWriter fw, 
							String traceName,
							String sessionName,
							String runName,
							String name,
							String experiementId,
							Boolean correct, 
							Integer time,
							String questionType
							) throws IOException{
		fw.append(traceName).append(',').
			append(sessionName).append(',').
			append(runName).append(',').
			append(name).append(',').
			append(experiementId).append(',').
			append(correct.toString()).append(',').
			append(time.toString()).append('\n').
			append(questionType).append(',');
		
	}

}
