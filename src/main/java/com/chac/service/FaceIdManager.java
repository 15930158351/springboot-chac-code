package com.chac.service;

import com.alibaba.fastjson.JSON;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 腾讯云E证通 人脸识别相关
 */
@Service
@Slf4j
public class FaceIdManager {

    private final FaceidClient faceidClient;

    @Value(value = "${tencent.faceIdClient.merchantId:123}")
    private String merchantId;

    @Value(value = "${tencent.faceIdClient.assistElderly.merchantId:123}")
    private String elderlyMerchantId;

    public static List<String> WHITE_LIST = Lists.newArrayList("17188320008", "18018668825");

    @Value(value = "${tencent.phoneVerify.whiteList:123}")
    private String phoneVerifyWhiteList;

    public FaceIdManager(@Value(value = "${tencent.faceClient.secretId:123}") String secretId,
                         @Value(value = "${tencent.faceClient.secretKey:123}") String secretKey,
                         @Value(value = "${TencentCloud:Cos:Region:123}") String region) {
        Credential credential = new Credential(secretId, secretKey);
        this.faceidClient = new FaceidClient(credential, region);
    }

//    public ApiResult<FaceIdTokenGetResponse> getFaceIdToken(FaceIdTokenGetRequest faceIdRequest) throws TencentCloudSDKException {
//        //默认有活、传2则切换为 助老merchantId
//
//        String merchantIdUse = faceIdRequest.getMiniAppType() != null && faceIdRequest.getMiniAppType().intValue() == 2
//                ? elderlyMerchantId : this.merchantId;
//        log.info("临时测试 secretId {},secretKey {},region {},merchantId {}", faceidClient.getCredential().getSecretId(),
//                faceidClient.getCredential().getSecretKey(), faceidClient.getRegion(), merchantIdUse);
//
//        if (faceIdRequest == null) {
//            throw new IllegalArgumentException("获取人脸识别token参数不能为空");
//        }
//        if (StringUtils.isBlank(faceIdRequest.getIdCard()) || StringUtils.isBlank(faceIdRequest.getName())) {
//            throw new IllegalArgumentException("姓名或身份证号不能为空");
//        }
//
//        log.info("getFaceIdToken 参数: " + JSON.toJSONString(faceIdRequest));
//        //转换为腾讯云SDK 获取E证通token所需参数
//        GetEidTokenRequest getEidTokenReq = new GetEidTokenRequest();
//        getEidTokenReq.setMerchantId(merchantIdUse);
//        getEidTokenReq.setIdCard(faceIdRequest.getIdCard());
//        getEidTokenReq.setName(faceIdRequest.getName());
//
//        GetEidTokenConfig getEidTokenConfig = new GetEidTokenConfig();
//        getEidTokenConfig.setInputType("4");
//        getEidTokenReq.setConfig(getEidTokenConfig);
//
//        GetEidTokenResponse getEidTokenRes = null;
//        try {
//            getEidTokenRes = faceidClient.GetEidToken(getEidTokenReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "getFaceIdToken 调取腾讯云E证通token失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw e;
//        }
//
//        FaceIdTokenGetResponse result = new FaceIdTokenGetResponse(
//                getEidTokenRes.getEidToken(),
//                getEidTokenRes.getUrl(),
//                getEidTokenRes.getRequestId()
//        );
//        return ApiResult.success(result, "获取人脸识别token成功");
//
//    }
//
//
//    /**
//     * 0 成功
//     * 2016 比对人脸信息不匹配(10004)
//     * 17 本次流程未完成
//     *
//     * @param checkRequest
//     * @return
//     * @throws TencentCloudSDKException
//     */
//    public ApiResult<Boolean> checkFaceToken(FaceIdCheckRequest checkRequest) throws TencentCloudSDKException {
//        if (checkRequest == null || StringUtils.isBlank(checkRequest.getFaceIdToken())) {
//            throw new IllegalArgumentException("获取人脸识别结果不能为空");
//        }
//
//        GetEidResultRequest getEidResultReq = new GetEidResultRequest();
//        String token = checkRequest.getFaceIdToken();
//        getEidResultReq.setEidToken(token);
//        log.info("checkFaceToken 参数: " + JSON.toJSONString(getEidResultReq));
//
//        GetEidResultResponse getEidResultRes = null;
//        try {
//            getEidResultRes = faceidClient.GetEidResult(getEidResultReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "checkFaceToken 调取腾讯云E证通人脸核身结果失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw e;
//        }
//
//        log.info("checkFaceToken {} 腾讯云SDK返回值: {} ", token, JSON.toJSONString(getEidResultRes));
//        if (getEidResultRes != null && getEidResultRes.getText() != null) {
//            DetectInfoText detectInfoText = getEidResultRes.getText();
//            if (detectInfoText.getErrCode() != null && detectInfoText.getErrCode() == 0) {
//                return ApiResult.success(true, "人脸识别通过");
//            }
//        } else {
//            return ApiResult.error(ResultCode.FAILED, getEidResultRes.getText().getErrMsg());
//        }
//        return ApiResult.error(ResultCode.FAILED, getEidResultRes.getText().getErrMsg());
//
//    }
//
//    public ApiResult<PhoneVerifyResponse> phoneVerification(PhoneVerifyRequest checkRequest) throws TencentCloudSDKException {
//        if (checkRequest == null || StringUtils.isBlank(checkRequest.getName())
//                || StringUtils.isBlank(checkRequest.getPhone()) || StringUtils.isBlank(checkRequest.getIdCard())) {
//            throw new IllegalArgumentException("参数不能为空");
//        }
//        List<String> whitePhoneList = Splitter.on(",").splitToList(phoneVerifyWhiteList);
//        if (whitePhoneList.contains(checkRequest.getPhone())) {
//            log.info("phoneVerification 白名单手机号: " + checkRequest.getPhone());
//            return ApiResult.success(new PhoneVerifyResponse("0", "白名单手机号"));
//        }
//
//
//        PhoneVerificationRequest phoneReq = new PhoneVerificationRequest();
//        phoneReq.setPhone(checkRequest.getPhone());
//        phoneReq.setName(checkRequest.getName());
//        phoneReq.setIdCard(checkRequest.getIdCard());
//        log.info("phoneVerification 参数: " + JSON.toJSONString(phoneReq));
//
//        PhoneVerificationResponse phoneVerifyRes = null;
//        try {
//            phoneVerifyRes = faceidClient.PhoneVerification(phoneReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "phoneVerification 调取腾讯云 三要素核验接口失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw e;
//        }
//
//        log.info("phoneVerification {} 腾讯云SDK返回值: {} ", JSON.toJSONString(phoneReq), JSON.toJSONString(phoneVerifyRes));
//        if (phoneVerifyRes != null) {
//            return ApiResult.success(new PhoneVerifyResponse(phoneVerifyRes.getResult(), phoneVerifyRes.getDescription()));
//        }
//
//        return ApiResult.error(ResultCode.FAILED, "三要素核验接口异常");
//    }
//
//    public ApiResult<CheckFaceTokenRes> checkFaceTokenV2(FaceIdCheckRequest checkRequest) throws TencentCloudSDKException {
//        if (checkRequest == null || StringUtils.isBlank(checkRequest.getFaceIdToken())) {
//            throw new IllegalArgumentException("获取人脸识别结果不能为空");
//        }
//
//        GetEidResultRequest getEidResultReq = new GetEidResultRequest();
//        String token = checkRequest.getFaceIdToken();
//        getEidResultReq.setEidToken(token);
//        log.info("checkFaceToken 参数: " + JSON.toJSONString(getEidResultReq));
//
//        GetEidResultResponse getEidResultRes = null;
//        try {
//            getEidResultRes = faceidClient.GetEidResult(getEidResultReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "checkFaceToken 调取腾讯云E证通人脸核身结果失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw e;
//        }
//
//        log.info("checkFaceToken {} 腾讯云SDK返回值: {} ", token, JSON.toJSONString(getEidResultRes));
//        if (getEidResultRes != null && getEidResultRes.getText() != null) {
//            DetectInfoText detectInfoText = getEidResultRes.getText();
//            if (detectInfoText.getErrCode() != null && detectInfoText.getErrCode() == 0) {
//                CheckFaceTokenRes checkFaceTokenRes = new CheckFaceTokenRes();
//                checkFaceTokenRes.setPass(true);
//                if (getEidResultRes.getBestFrame() != null) {
//                    checkFaceTokenRes.setBestFrame(getEidResultRes.getBestFrame().getBestFrame());
//                }
//                return ApiResult.success(checkFaceTokenRes, "人脸识别通过");
//            }
//        } else {
//            return ApiResult.error(ResultCode.FAILED, getEidResultRes.getText().getErrMsg());
//        }
//        return ApiResult.error(ResultCode.FAILED, getEidResultRes.getText().getErrMsg());
//
//    }
}
