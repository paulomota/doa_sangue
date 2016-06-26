package br.com.doasangue.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.deltaspike.core.util.StringUtils;
import org.apache.http.impl.cookie.DateUtils;

public class DateUtil /*extends org.apache.commons.lang3.time.DateUtils*/ {

	// FORMATAR DATE'S
	public static final SimpleDateFormat dmaAShmsLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat dmaAShms = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss");
	public static final SimpleDateFormat dma = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat dma_hms = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final SimpleDateFormat hmsms = new SimpleDateFormat("HH:mm:ss.SSS");
	public static final SimpleDateFormat amd_hms = new SimpleDateFormat("yyyyMMdd-HHmmss");
	public static final SimpleDateFormat amdhms = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat amd = new SimpleDateFormat("yyyyMMdd");

	private static final long SEGUNDO_EM_MILLIS = 1000L;
	private static final long MINUTO_EM_MILLIS = SEGUNDO_EM_MILLIS * 60L;
	private static final long HORA_EM_MILLIS = MINUTO_EM_MILLIS * 60L;
	private static final long DIA_EM_MILLIS = HORA_EM_MILLIS * 24L;
	private static final long MES_EM_MILLIS = DIA_EM_MILLIS * 24L;
	private static final long ANO_EM_MILLIS = MES_EM_MILLIS * 24L;

	/**
	 * Retorna uma lista com todos os meses do ano
	 * @return
	 */
	public static List<Integer> getMesesList(){
		List<Integer> meses = new ArrayList<Integer>();
		meses.add(1);
		meses.add(2);
		meses.add(3);
		meses.add(4);
		meses.add(5);
		meses.add(6);
		meses.add(7);
		meses.add(8);
		meses.add(9);
		meses.add(10);
		meses.add(11);
		meses.add(12);
		
		return meses;
	}
	
	/**
	 * Retorna uma lista de anos começando pelo ano atual e indo até 
	 * a quantidade informada
	 * @return
	 */
	public static List<Integer> getAnosList(int quantidadeAnos){
		List<Integer> anos = new ArrayList<Integer>();
		
		for (int i = getCurrentYear(); i <= getCurrentYear() + quantidadeAnos; i++) {
			anos.add(i);
		}
		
		return anos;
	}
	
	public static String convertDateToStringDateTime(Date data){
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date convertStringToDateCETSP(String dataStr){
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date convertStringToDataHoraMinuto(String dateString) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Long segundosEmMillis(Integer segundos) {
		return segundos * SEGUNDO_EM_MILLIS;
	}

	public static Long minutosEmMillis(Integer minutos) {
		return minutos * MINUTO_EM_MILLIS;
	}

	public static Long horasEmMillis(Integer horas) {
		return horas * HORA_EM_MILLIS;
	}

	public static Long diasEmMillis(Integer dias) {
		return dias * DIA_EM_MILLIS;
	}

	public static Long mesesEmMillis(Integer meses) {
		return meses * MES_EM_MILLIS;
	}

	public static Long anosEmMillis(Integer anos) {
		return anos * ANO_EM_MILLIS;
	}

	public static final int diferencaEmMeses(Date date1, Date date2) {

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		int m1 = cal1.get(Calendar.YEAR) * 12 + cal1.get(Calendar.MONTH);
		int m2 = cal2.get(Calendar.YEAR) * 12 + cal2.get(Calendar.MONTH);
		return m2 - m1 + 1;
	}

	/**
	 * M�todo Long getDaysDiff(Date start, Date end) responsavel por recuperar
	 * DaysDiff.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @return DaysDiff.
	 */
	public static Long getDaysDiff(Date start, Date end) {

		start = zeroTime(start);
		end = zeroTime(end);

		Calendar cStart = new GregorianCalendar();
		cStart.setLenient(false);
		cStart.setTime(start);

		Calendar cEnd = new GregorianCalendar();
		cEnd.setLenient(false);
		cEnd.setTime(end);

		Long diff = cEnd.getTimeInMillis() - cStart.getTimeInMillis();

		return diff / (24 * 60 * 60 * 1000);

	}

	public static boolean mesmoDia(Date start, Date end) {

		start = zeroTime(start);
		end = zeroTime(end);

		Calendar cStart = new GregorianCalendar();
		cStart.setLenient(false);
		cStart.setTime(start);

		Calendar cEnd = new GregorianCalendar();
		cEnd.setLenient(false);
		cEnd.setTime(end);

		return cStart.getTimeInMillis() - cEnd.getTimeInMillis() == 0;
	}

	public static Date entradaHorarioFuncionamentoHoje() {
		return entradaHorarioDeFuncionamento(new Date());
	}

	public static Date entradaHorarioDeFuncionamento(Date dia) {
		Calendar entrada = new GregorianCalendar();
		entrada.setLenient(false);
		entrada.setTime(dia);
		entrada.set(Calendar.HOUR_OF_DAY, 8);
		entrada.set(Calendar.MINUTE, 0);
		entrada.set(Calendar.SECOND, 0);
		entrada.set(Calendar.MILLISECOND, 0);
		return entrada.getTime();
	}

	public static Date saidaHorarioDeFuncionamentoDiaUtil(Date dia) {
		Calendar entrada = new GregorianCalendar();
		entrada.setLenient(false);
		entrada.setTime(dia);
		entrada.set(Calendar.HOUR_OF_DAY, 18);
		entrada.set(Calendar.MINUTE, 0);
		entrada.set(Calendar.SECOND, 0);
		entrada.set(Calendar.MILLISECOND, 0);
		return entrada.getTime();
	}

	public static Date getBeginDayDate(Date data){
		Calendar entrada = new GregorianCalendar();
		entrada.setTime(data);
		entrada.set(Calendar.HOUR_OF_DAY, 0);
		entrada.set(Calendar.MINUTE, 0);
		entrada.set(Calendar.SECOND, 0);
		return entrada.getTime();
	}
	
	public static Date getEndDayDate(Date data){
		Calendar entrada = new GregorianCalendar();
		entrada.set(Calendar.HOUR_OF_DAY, 23);
		entrada.set(Calendar.MINUTE, 59);
		entrada.set(Calendar.SECOND, 59);
		return entrada.getTime();
	}
	
	public static Date saidaHorarioDeFuncionamentoSabado(Date dia) {
		Calendar entrada = new GregorianCalendar();
		entrada.setLenient(false);
		entrada.setTime(dia);
		entrada.set(Calendar.HOUR_OF_DAY, 13);
		entrada.set(Calendar.MINUTE, 0);
		entrada.set(Calendar.SECOND, 0);
		entrada.set(Calendar.MILLISECOND, 0);
		return entrada.getTime();
	}

	/**
	 * Metodo Long getMinutesDiff(Date start, Date end) responsavel por
	 * recuperar MinutesDiff.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @return MinutesDiff.
	 */
	public static Long getMinutesDiff(Date start, Date end) {

		Calendar cStart = new GregorianCalendar();
		cStart.setLenient(false);
		cStart.setTime(start);
		cStart.clear(Calendar.SECOND);
		cStart.clear(Calendar.MILLISECOND);

		Calendar cEnd = new GregorianCalendar();
		cEnd.setLenient(false);
		cEnd.setTime(end);
		cEnd.clear(Calendar.SECOND);
		cEnd.clear(Calendar.MILLISECOND);

		Long diff = cEnd.getTimeInMillis() - cStart.getTimeInMillis();

		return diff / (60 * 1000);
	}

	public static Long getHoursDiff(Date start, Date end) {
		start = zerarMinutos(start);
		end = zerarMinutos(end);
		// System.out.println("hora do fim do dia: " + end.getHours());
		Long diff = (end.getTime() - start.getTime()) / (60 * 60 * 1000);
		return diff;
	}

	public static Long diferencaEntreHoras(Date start, Date end) {
		start = zerarMinutos(start);
		end = zerarMinutos(end);
		Long diff = (end.getTime() - start.getTime()) / (60 * 60 * 1000);
		return diff;
	}

	/**
	 * Zero time.
	 * 
	 * @param data
	 *            the data
	 * @return the date
	 */
	public static Date zeroTime(Date data) {

		Calendar c = new GregorianCalendar();
		c.setTime(data);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * Final hour of date.
	 * 
	 * @param data
	 *            the data
	 * @return the date
	 */
	public static Date finalHourOfDate(Date data) {

		Calendar c = new GregorianCalendar();
		c.setTime(data);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 000);

		return c.getTime();
	}

	/**
	 * Zero date.
	 * 
	 * @param data
	 *            the data
	 * @return the date
	 */
	public static Date zeroDate(Date data) {

		Calendar c = new GregorianCalendar();
		c.setTime(data);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.YEAR, 1970);

		return c.getTime();
	}

	/**
	 * Zerar milisegundos.
	 * 
	 * @param data
	 *            the data
	 * @return the date
	 */
	public static Date zerarMilisegundos(Date data) {

		Calendar c = new GregorianCalendar();
		c.setTime(data);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();

	}

	public static Date zerarSegundos(Date data) {
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();

	}
	
	public static Date setHour(Date data, Integer hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR, hour);

		return cal.getTime();
	}

	public static Date zerarMinutos(Date data) {

		Calendar c = new GregorianCalendar();
		c.setTime(data);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);

		return c.getTime();

	}

	/**
	 * Soma horas na data.
	 * 
	 * @param data
	 *            the data
	 * @param hora
	 *            the hora
	 * @return the date
	 */
	public static Date somaHorasNaData(Date data, Date hora) {

		long dataEmMilisegundos = data.getTime();

		Calendar horaBase = Calendar.getInstance();
		horaBase.setTime(hora);
		// X horas passadas no parametro 'hora'
		long horaBaseAsXHorasEmMilisegundos = horaBase.getTimeInMillis();
		// meia noite: base para o c�lculo de quantos millisegundos � a
		// 'hora'.
		horaBase.set(Calendar.HOUR_OF_DAY, 0);
		horaBase.set(Calendar.MINUTE, 0);
		horaBase.set(Calendar.SECOND, 0);
		long horaBaseAMeiaNoite = horaBase.getTimeInMillis();
		// 'hora' em milisegundos.
		long xHoras = horaBaseAsXHorasEmMilisegundos - horaBaseAMeiaNoite;
		// adiciona x 'horas' a 'data'.
		Date dataMaisXHoras = new Date(dataEmMilisegundos + xHoras);

		return dataMaisXHoras;

	}

	/**
	 * Soma minutos na data.
	 * 
	 * @param data
	 *            the data
	 * @param minutos
	 *            the minutos
	 * @return the date
	 */
	public static Date somaMinutosNaData(Date data, Long minutos) {

		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.MINUTE, minutos.intValue());

		return c.getTime();

	}

	/**
	 * Soma meses.
	 * 
	 * @param data
	 *            the data
	 * @param meses
	 *            the meses
	 * @return the date
	 */
	public static Date somaMeses(Date data, int meses) {

		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.MONTH, meses);
		return c.getTime();
	}

	/**
	 * Soma dias.
	 * 
	 * @param data
	 *            the data
	 * @param dias
	 *            the dias
	 * @return the date
	 */
	public static Date somaDias(Date data, int dias) {

		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_YEAR, dias);
		return c.getTime();
	}

	/**
	 * To milisegundos.
	 * 
	 * @param data
	 *            the data
	 * @return the long
	 */
	public static Long toMilisegundos(Date data) {

		Calendar c = new GregorianCalendar();
		c.setTime(data);

		return c.getTimeInMillis();
	}

	/**
	 * M�todo Date getOntem(Date data) responsavel por recuperar Ontem.
	 * 
	 * @param data
	 *            the data
	 * @return Ontem.
	 */
	public static Date getOntem(Date data) {

		return getDiaAnteriorOuConseguinte(data, -1);

	}

	/**
	 * M�todo Date getAmanha(Date data) responsavel por recuperar Amanha.
	 * 
	 * @param data
	 *            the data
	 * @return Amanha.
	 */
	public static Date getAmanha(Date data) {

		return getDiaAnteriorOuConseguinte(data, 1);

	}

	/**
	 * M�todo Date getDiaAnteriorOuConseguinte(Date data, int numeroDeDias)
	 * responsavel por recuperar DiaAnteriorOuConseguinte.
	 * 
	 * @param data
	 *            the data
	 * @param numeroDeDias
	 *            the numero de dias
	 * @return DiaAnteriorOuConseguinte.
	 */
	public static Date getDiaAnteriorOuConseguinte(Date data, int numeroDeDias) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, numeroDeDias);

		return c.getTime();

	}

	// public static Date seteHorasDoDiaAnterior() {
	// Calendar calendario = Calendar.getInstance();
	// calendario.setTime(new Date());
	// }
	//
	// public static Date seteHorasDeHoje() {
	//
	// }

	/**
	 * Esse m�todo transforma uma <code>String</code> no formato <b>HH:mm</b> em
	 * um valor em minutos.
	 * 
	 * @param horaMinuto
	 *            - String.
	 * @return Long com os minutos gastos nesse tempo passado em
	 *         <i>horaMinuto</i>.
	 */
	public static Long transformaHoraMinutoEmMinuto(String horaMinuto) {
		// tem que ter um e apenas um ':'
		if (horaMinuto.indexOf(':') >= 0 && (horaMinuto.indexOf(':') == horaMinuto.lastIndexOf(':'))) {
			String[] splitted = horaMinuto.split(":");

			String horaAux = splitted[0].trim();
			String minutoAux = splitted[1].trim();
			try {
				// erro na representacao
				if (StringUtils.isEmpty(horaAux) || StringUtils.isEmpty(minutoAux)) {
					return null;
				}
				// somente numeros.
				if (!horaAux.matches("\\d+") || !minutoAux.matches("\\d+")) {
					return null;
				}

				// if (Long.parseLong(horaAux) < 0 || Long.parseLong(minutoAux)
				// < 0) {
				// return null;
				// }
				return (Long.parseLong(horaAux) * 60) + Long.parseLong(minutoAux);
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Formata minuto em hora minuto.
	 * 
	 * @param minutos
	 *            the minutos
	 * @return the string
	 */
	public static String formataMinutoEmHoraMinuto(Long minutos) {
		Long minuto = Long.valueOf(minutos % 60L);
		Long hora = Long.valueOf(minutos / 60L);
		String retorno = null;

		if (minutos >= 0) {
			if (hora.toString().length() < 2) {
				retorno = "0" + hora + ":";
			} else {
				retorno = hora + ":";
			}
			if (minuto.toString().length() < 2) {
				retorno += "0" + minuto + ":";
			} else {
				retorno += minuto + ":";
			}
		}
		return retorno;

	}

	/**
	 * Formata minuto em hora minuto.
	 * 
	 * @param minutos
	 *            the minutos
	 * @return the string
	 */
	public static String formataMinutoEmHora(Long minutos) {
		Long hora = Long.valueOf(minutos / 60L);
		String retorno = null;

		if (minutos >= 0) {
			if (hora.toString().length() < 2) {
				retorno = "0" + hora;
			} else {
				retorno = hora.toString();
			}
		}
		return retorno;

	}

	/**
	 * Transforma hora minuto em date.
	 * 
	 * @param horaMinuto
	 *            the hora minuto
	 * @return the date
	 */
	public static Date transformaHoraMinutoEmDate(String horaMinuto) {
		try {
			String[] splitted = horaMinuto.split(":");
			if (splitted.length != 2) {
				return null;
			}

			int hora = new Integer(splitted[0].trim()).intValue();
			int minuto = new Integer(splitted[1].trim()).intValue();

			Calendar c = new GregorianCalendar();
			c.setTime(zeroDate(zeroTime(new Date())));
			c.set(Calendar.HOUR_OF_DAY, hora);
			c.set(Calendar.MINUTE, minuto);

			return zeroDate(c.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Transforma date em hora minuto.
	 * 
	 * @param data
	 *            the data
	 * @return the string
	 */
	public static String transformaDateEmHoraMinuto(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));
		return sdf.format(data);
	}

	/**
	 * Formata data ddmmyyy.
	 * 
	 * @param data
	 *            the data
	 * @return the string
	 */
	public static String formataDataDDMMYYY(Date data) {
		return new SimpleDateFormat("dd/MM/yyyy").format(data);
	}

	public static String formataDataDDMMYYYHHmmSS(Date data) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:SS").format(data);
	}
	
	public static String formataHorarioHHmmss(Date data) {
		return new SimpleDateFormat("HH:mm:ss").format(data);
	}
	
	public static Date getPrimeiroDiaMes(Date data) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return zeroTime(cal.getTime());
	}

	public static Date getUltimoDiaMes(Date data) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		return cal.getTime();
	}

	public static Date getUltimoMomentoDoMes(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return finalHourOfDate(c.getTime());
	}

	public static Date getPrimeiroMomentoDoMes(Date data) {
		return zeroTime(getPrimeiroDiaMes(data));
	}

	public static int diasUteisEntreDatas(Date inicio, Date fim) {

		Calendar inicioCalendar = Calendar.getInstance();
		inicioCalendar.setTime(inicio);

		Calendar fimCalendar = Calendar.getInstance();
		fimCalendar.setTime(fim);

		if (inicioCalendar.getTimeInMillis() == fimCalendar.getTimeInMillis()) {
			return 0;
		}

		if (inicioCalendar.getTimeInMillis() > fimCalendar.getTimeInMillis()) {
			inicioCalendar.setTime(fim);
			fimCalendar.setTime(inicio);
		}

		int diasUteis = 0;

		do {

			inicioCalendar.add(Calendar.DAY_OF_MONTH, 1);

			if (inicioCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& inicioCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++diasUteis;
			}
		} while (inicioCalendar.getTimeInMillis() <= fimCalendar.getTimeInMillis());

		return diasUteis;
	}
	
	public static Date addMinutsTodate(Date inititalDate, int minuts) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(inititalDate); 
		cal.add(Calendar.MINUTE, minuts); 
		
		Date futureDate = cal.getTime(); 
		
		return zerarMilisegundos(futureDate);
	}
	
	public static boolean isBeforeThanNow(Date comparableDate){
		return (new Date()).before(comparableDate);
	}

	public static Integer getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		return cal.get(Calendar.YEAR);
	}
	
}
