/**
 * Copyright © 2004-2016 LianlianPay.All Rights Reserved.
 */
package com.exchange.pab.dmz.task;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exchange.pab.dmz.config.PinganConfig;
import com.exchange.pab.dmz.thread.NoticeThread;
import com.exchange.pab.dmz.utils.DateTimeUtils;
import com.exchange.pab.dmz.utils.FileOperator;
import com.exchange.pab.dmz.utils.MessageBuildUtil;
import com.exchange.pab.domain.PinganFileUploadRequest;
import com.lianpay.bankcustody.notifylog.domain.CustodyTraderInfo;
import com.lianpay.bankcustody.notifylog.service.CustodyTraderInfoService;
import com.lianpay.paybill.domain.PayBill;
import com.lianpay.query.domain.QueryPageResult;
import com.lianpay.query.dubbo.service.PayBillQueryService;
import com.lianpay.share.tcp.SocketClient;
import com.lianpay.share.utils.FuncUtils;

/**
 * 
 * 描述说明
 * 
 * @version V1.0
 * @author chencheng@yintong.com.cn
 * @Date 2017年7月4日 下午6:40:20
 * @since JDK 1.6
 */

public class FileGeneratJob {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PayBillQueryService payBillQueryService;// = SpringContext.getBean("payBillQueryService");

	@Autowired
	CustodyTraderInfoService custodyTraderInfoService;// = SpringContext.getBean("custodyTraderInfoService");

	@Autowired
	PinganConfig config;// = SpringContext.getBean("pinganConfig");

	public void generatPayFile() throws Exception {
		String tag = "ZF" + DateTimeUtils.getCurrentDateTimeStr();
		logger.info(tag + "：支付明细对账文件生成任务启动...");
		// 按照商户号、日期范围
		Date operatDate = DateTimeUtils.getTimesMoning(config.fileDays);

		generatFile(tag, operatDate);
		
		logger.info("{}：支付明细对账文件生成任务结束...", tag);
	}

	/**
	* 方法描述
	*
	* @param tag
	* @param beginTime
	* @param dt_start
	* @throws Exception
	* @throws ParseException
	*/
	public void generatFile(String tag, Date beginTime) throws Exception, ParseException {

		String dt_start = DateTimeUtils.dateFormat(beginTime, DateTimeUtils.DEFAUT_DATE_FORMAT);
		// 获取商户号，循环
		List<CustodyTraderInfo> bankCustody = custodyTraderInfoService.queryCustodyTraderInfoByPlatform("4");// 平安存管商户
		for (CustodyTraderInfo custodyTraderInfo : bankCustody) {
			if ("wyo".equals(custodyTraderInfo.getBizType())) {
				continue;
			}

			logger.info("{}：开始生成商户[{}]的支付对账文件", tag, custodyTraderInfo.getOidPartner());

			PayBill ayBill = new PayBill();
			// ayBill.setOid_chnl("15");
			ayBill.setPay_type("D");
			ayBill.setCol_custid(custodyTraderInfo.getOidPartner());
			// ayBill.setDt_start(dt_start);
			// ayBill.setDt_end(dt_start);
			ayBill.setDate_acct(dt_start);
			ayBill.setMaxrecordes(String.valueOf(Integer.MAX_VALUE));
			ayBill.setOffset("0");
			QueryPageResult result = payBillQueryService.querBillByPageWithCheck(ayBill);
			if (null == result) {
				continue;
			}
			List<PayBill> bills = result.getResultList();
			if (null == bills || 0 == bills.size()) {
				continue;
			}
			// 生成文件
			String filePath = config.filePath;
			// ZF-三方支付平台代码-网贷平台代码-交易日期（YYMMDD）-10位序号-REQ.txt
			StringBuilder fileName = new StringBuilder("ZF").append("-");
			fileName.append(config.thirdPayQydm).append("-").append(custodyTraderInfo.getCustodyNo()).append("-")
					.append(DateTimeUtils.dateFormat(beginTime, DateTimeUtils.DEFAUT_DATE_FORMAT)).append("-")
					.append(DateTimeUtils.getCurrentDateTimeStr().substring(4)).append("-")
					.append("REQ.txt");

			FileOperator.writeTxtFile(bills.size() + "", new File(filePath + fileName));

			StringBuilder content = new StringBuilder();
			for (PayBill payBill : bills) {
				content = new StringBuilder();
				content.append(config.thirdPayQydm).append("&")// 三方支付平台代码
						.append(custodyTraderInfo.getCustodyNo()).append("&")// P2P平台代码
						.append(payBill.getOrder_id()).append("&")// 支付订单号
						.append(DateTimeUtils.dateFormat2(payBill.getDate_success(), DateTimeUtils.DEFAUT_DATE_FORMAT)).append("&")// 支付日期YYYYMMDD
						.append(DateTimeUtils.dateFormat2(payBill.getDate_success(), DateTimeUtils.DEFAUT_TIME_FORMAT)).append("&")// 支付时间HH24MISS
						.append(payBill.getPaycust_acct()).append("&")// 付款账号
						.append(payBill.getPaycust_name()).append("&")// 付款账户名称
						.append(FuncUtils.formatYuanMoney(payBill.getAmt_paybill())).append("&")// 交易金额
						.append("0").append("&")// 交易手续费
						.append("1").append("&");// 到账标识

				FileOperator.contentToTxt(filePath + fileName, content.toString());

			}

			logger.info("{}：开始上传商户[{}]的支付对账文件，路径：{}", tag, custodyTraderInfo.getOidPartner(), filePath + fileName);

			PinganFileUploadRequest request = new PinganFileUploadRequest();
			request.setFileName(fileName.toString());
			request.setFilePath(filePath);
			request.setTradeSn("ZF" + custodyTraderInfo.getCustodyNo() + DateTimeUtils.getCurrentDateTimeStr().substring(4));// 每次文件上传、下载请求唯一。可取报文头字段“请求方系统流水号”。
			request.setP2PQydm(custodyTraderInfo.getCustodyNo());
			request.setThirdPayQydm(config.thirdPayQydm);

			String reqMsg = MessageBuildUtil.buildFileUploadRequest(request);
			// 发送银行
			logger.info("{}交易请求报文:{}", tag, reqMsg);
			String resMsg = SocketClient.sendMessage(reqMsg, config.timeout, config.payNotifyUrl, config.port, config.charset, tag);
			logger.info("{}交易返回报文:{}", tag, resMsg);

			// 文件通知
			NoticeThread noticeThread = new NoticeThread(request, tag);
			noticeThread.run();
		}
	}
}
