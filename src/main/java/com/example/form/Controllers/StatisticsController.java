package com.example.form.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.form.Entities.Statistics;
import com.example.form.Repository.StatisticsRepository;

@Component
public class StatisticsController {
	@Autowired 
	StatisticsRepository StatRepo;
	
public List<Statistics> getStatistics()
{
	Iterable<Statistics> allstatistics =StatRepo.findAll();
	
	List<Statistics>Statlist= new ArrayList<>();
	for (Statistics e:allstatistics)
	{if (e==null)
		break;
		if (!e.getStatus())
		Statlist.add(e);
	}
	if (Statlist.isEmpty())
		return null;
	
	return Statlist;
}

public void AproveStatistics(String[] approvedStat) {
	for (int i = 0; i < approvedStat.length; i++) 
	{
		StatRepo.deleteById(approvedStat[i]);
		StatRepo.save(new Statistics(approvedStat[i],true));
	
	}
	
}
}
