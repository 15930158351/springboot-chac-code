package com.chac.service;


import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 腾讯云OCR SDK调用及参数转换
 */
@Service
@Slf4j
public class TencentOCRManager {


    private final OcrClient ocrClient;

    public static final String CARD_SIDE_FRONT = "FRONT";

    public TencentOCRManager(@Value(value = "${TencentCloud:SecretId}") String secretId,
                             @Value(value = "${TencentCloud:SecretKey}") String secretKey,
                             @Value(value = "${TencentCloud:Cos:Region}") String region) {
        Credential credential = new Credential(secretId, secretKey);
        this.ocrClient = new OcrClient(credential, region);
    }
//
//    /**
//     * 身份证识别
//     *
//     * @param ocrRequest
//     * @return
//     */
//    public ApiResult<HoppedIdCardOcrResponse> idCardOcr(HoppedIdCardOcrRequest ocrRequest) {
//        if (ocrRequest == null) {
//            throw new IllegalArgumentException("身份证识别OCR参数不能为空");
//        }
//        if (StringUtils.isBlank(ocrRequest.getImageBase64()) && StringUtils.isBlank(ocrRequest.getImageUrl())) {
//            throw new IllegalArgumentException("图片Base64值和图片Url不能同时为空");
//        }
//
//        log.info("idCardOCR 参数: " + JSON.toJSONString(ocrRequest));
//        //转换为腾讯云SDK OCR所需参数
//        IDCardOCRRequest tencentOCRReq = new IDCardOCRRequest();
//        String cardSide = StringUtils.isBlank(ocrRequest.getCardSide()) ? CARD_SIDE_FRONT : ocrRequest.getCardSide();
//        tencentOCRReq.setCardSide(cardSide);
//        if (StringUtils.isNotBlank(ocrRequest.getImageBase64())) {
//            tencentOCRReq.setImageBase64(ocrRequest.getImageBase64());
//        }
//        if (StringUtils.isNotBlank(ocrRequest.getImageUrl())) {
//            tencentOCRReq.setImageUrl(ocrRequest.getImageUrl());
//        }
//        //如果是正面 返回人像照片裁剪（自动抠取身份证头像区域）
//        if (CARD_SIDE_FRONT.equalsIgnoreCase(cardSide)) {
//            Map<String, Object> configMap = new HashMap<>();
//            configMap.put("CropPortrait", true);
//            tencentOCRReq.setConfig(JSON.toJSONString(configMap));
//        }
//
//        IDCardOCRResponse tencentOCRRes = null;
//        try {
//            tencentOCRRes = ocrClient.IDCardOCR(tencentOCRReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "idCardOCR 调取腾讯云OCR失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw new IllegalArgumentException(errorMsg);
//        }
//
//        log.info("idCardOCR 腾讯云SDK返回值: " + JSON.toJSONString(tencentOCRRes));
//        HoppedIdCardOcrResponse ocrResponse = buildHoppedIdCardOcrResponse(tencentOCRRes);
//        return ApiResult.success(ocrResponse, "身份证OCR识别成功");
//    }
//
//    /**
//     * 将腾讯云SDK返回值转换为自定义返回值
//     * 能copyProperties就copy 不能再手动set
//     */
//    private HoppedIdCardOcrResponse buildHoppedIdCardOcrResponse(IDCardOCRResponse tencentOCRRes) {
//        HoppedIdCardOcrResponse ocrResponse = new HoppedIdCardOcrResponse();
//        BeanUtils.copyProperties(tencentOCRRes, ocrResponse);
//
//        //告警码 转换为逗号分隔的数字
//        if (StringUtils.isNotBlank(tencentOCRRes.getAdvancedInfo())) {
//            Map<String, Object> advancedMap = JSON.parseObject(tencentOCRRes.getAdvancedInfo(), Map.class);
//            if (advancedMap != null && advancedMap.containsKey("WarnInfos")) {
//                ocrResponse.setWarnCodes(Joiner.on(",").join((JSONArray) advancedMap.get("WarnInfos")));
//            }
//            if (advancedMap != null && advancedMap.containsKey("Portrait")) {
//                ocrResponse.setPortrait(String.valueOf(advancedMap.get("Portrait")));
//            }
//        }
//        return ocrResponse;
//    }
//
//    /**
//     * 驾驶证识别
//     *
//     * @param ocrRequest
//     * @return
//     */
//    public ApiResult<HoppedLicenseOcrResponse> driverLicenseOCR(HoppedLicenseOcrRequest ocrRequest) {
//
//        if (ocrRequest == null) {
//            throw new IllegalArgumentException("驾驶证识别OCR参数不能为空");
//        }
//        if (StringUtils.isBlank(ocrRequest.getImageBase64()) && StringUtils.isBlank(ocrRequest.getImageUrl())) {
//            throw new IllegalArgumentException("图片Base64值和图片Url不能同时为空");
//        }
//
//        log.info("driverLicenseOCR 参数: " + JSON.toJSONString(ocrRequest));
//        //转换为腾讯云SDK OCR所需参数
//        DriverLicenseOCRRequest tencentOCRReq = new DriverLicenseOCRRequest();
//        String cardSide = StringUtils.isBlank(ocrRequest.getCardSide()) ? CARD_SIDE_FRONT : ocrRequest.getCardSide();
//        tencentOCRReq.setCardSide(cardSide);
//        if (StringUtils.isNotBlank(ocrRequest.getImageBase64())) {
//            tencentOCRReq.setImageBase64(ocrRequest.getImageBase64());
//        }
//        if (StringUtils.isNotBlank(ocrRequest.getImageUrl())) {
//            tencentOCRReq.setImageUrl(ocrRequest.getImageUrl());
//        }
//
//        DriverLicenseOCRResponse tencentOCRRes = null;
//        try {
//            tencentOCRRes = ocrClient.DriverLicenseOCR(tencentOCRReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "driverLicenseOCR 调取腾讯云OCR失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw new IllegalArgumentException(errorMsg);
//        }
//
//        log.info("driverLicenseOCR 腾讯云SDK返回值: " + JSON.toJSONString(tencentOCRRes));
//        HoppedLicenseOcrResponse ocrResponse = buildHoppedLicenseOcrResponse(tencentOCRRes);
//
//        //TODO 保存到数据库 不一定需要 看下具体需求
//        return ApiResult.success(ocrResponse, "驾驶证OCR识别成功");
//    }
//
//    /**
//     * 将腾讯云SDK返回值转换为自定义返回值
//     * 能copyProperties就copy 不能再手动set
//     */
//    private HoppedLicenseOcrResponse buildHoppedLicenseOcrResponse(DriverLicenseOCRResponse tencentOCRRes) {
//        HoppedLicenseOcrResponse ocrResponse = new HoppedLicenseOcrResponse();
//        BeanUtils.copyProperties(tencentOCRRes, ocrResponse);
//
//        ocrResponse.setCardNo(tencentOCRRes.getCardCode());
//        ocrResponse.setClassType(tencentOCRRes.getClass_());
//        //告警码 逗号分割的数字
//        if (ArrayUtil.isNotEmpty(tencentOCRRes.getRecognizeWarnCode())) {
//            ocrResponse.setWarnCodes(Joiner.on(",").join(tencentOCRRes.getRecognizeWarnCode()));
//        }
//        if (ArrayUtil.isNotEmpty(tencentOCRRes.getRecognizeWarnMsg())) {
//            ocrResponse.setWarnMsg(Joiner.on(",").join(tencentOCRRes.getRecognizeWarnMsg()));
//        }
//        return ocrResponse;
//    }
//
//    /**
//     * 行驶证识别
//     *
//     * @param ocrRequest
//     * @return
//     */
//    public ApiResult<HoppedVehicleOcrResponse> vehicleLicenseOcr(HoppedVehicleOcrRequest ocrRequest) {
//        if (ocrRequest == null) {
//            throw new IllegalArgumentException("行驶证识别OCR参数不能为空");
//        }
//        if (StringUtils.isBlank(ocrRequest.getImageBase64()) && StringUtils.isBlank(ocrRequest.getImageUrl())) {
//            throw new IllegalArgumentException("图片Base64值和图片Url不能同时为空");
//        }
//
//        log.info("vehicleLicenseOCR 参数: " + JSON.toJSONString(ocrRequest));
//        //转换为腾讯云SDK OCR所需参数
//        VehicleLicenseOCRRequest tencentOCRReq = new VehicleLicenseOCRRequest();
//        String cardSide = StringUtils.isBlank(ocrRequest.getCardSide()) ? CARD_SIDE_FRONT : ocrRequest.getCardSide();
//        tencentOCRReq.setCardSide(cardSide);
//        if (StringUtils.isNotBlank(ocrRequest.getImageBase64())) {
//            tencentOCRReq.setImageBase64(ocrRequest.getImageBase64());
//        }
//        if (StringUtils.isNotBlank(ocrRequest.getImageUrl())) {
//            tencentOCRReq.setImageUrl(ocrRequest.getImageUrl());
//        }
//
//        VehicleLicenseOCRResponse tencentOCRRes = null;
//        try {
//            tencentOCRRes = ocrClient.VehicleLicenseOCR(tencentOCRReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "vehicleLicenseOCR 调取腾讯云OCR失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw new IllegalArgumentException(errorMsg);
//        }
//
//        log.info("vehicleLicenseOCR 腾讯云SDK返回值: " + JSON.toJSONString(tencentOCRRes));
//        HoppedVehicleOcrResponse ocrResponse = buildHoppedVehicleOcrResponse(tencentOCRRes);
//
//        //TODO 保存到数据库 不一定需要 看下具体需求
//        return ApiResult.success(ocrResponse, "行驶证OCR识别成功");
//
//    }
//
//    /**
//     * 将腾讯云SDK返回值转换为自定义返回值
//     * 能copyProperties就copy 不能再手动set
//     */
//    private HoppedVehicleOcrResponse buildHoppedVehicleOcrResponse(VehicleLicenseOCRResponse tencentOCRRes) {
//        HoppedVehicleOcrResponse ocrResponse = new HoppedVehicleOcrResponse();
//        //行驶证主页正面的识别结果 对应cardSide 为 FRONT
//        if (tencentOCRRes.getFrontInfo() != null) {
//            TextVehicleFront vehicleFrontRes = tencentOCRRes.getFrontInfo();
//            HoppedVehicleFront vehicleFront = new HoppedVehicleFront();
//            BeanUtils.copyProperties(vehicleFrontRes, vehicleFront);
//            ocrResponse.setVehicleFront(vehicleFront);
//        }
//        //行驶证副页正面的识别结果 对应cardSide 为 BACK
//        if (tencentOCRRes.getBackInfo() != null) {
//            TextVehicleBack vehicleBackRes = tencentOCRRes.getBackInfo();
//            HoppedVehicleBack vehicleBack = new HoppedVehicleBack();
//            BeanUtils.copyProperties(vehicleBackRes, vehicleBack);
//            ocrResponse.setVehicleBack(vehicleBack);
//        }
//        //告警码 逗号分割的数字
//        if (ArrayUtil.isNotEmpty(tencentOCRRes.getRecognizeWarnCode())) {
//            ocrResponse.setWarnCodes(Joiner.on(",").join(tencentOCRRes.getRecognizeWarnCode()));
//        }
//        if (ArrayUtil.isNotEmpty(tencentOCRRes.getRecognizeWarnMsg())) {
//            ocrResponse.setWarnMsg(Joiner.on(",").join(tencentOCRRes.getRecognizeWarnMsg()));
//        }
//        ocrResponse.setVehicleLicenseType(tencentOCRRes.getVehicleLicenseType());
//        ocrResponse.setRequestId(tencentOCRRes.getRequestId());
//        return ocrResponse;
//    }
//
//    /**
//     * 网约车驾驶证识别
//     *
//     * @param ocrRequest
//     * @return
//     */
//    public ApiResult<RideHailingLicenseOcrResponse> rideHailingLicenseOcr(RideHailingLicenseOcrRequest ocrRequest) {
//        if (ocrRequest == null) {
//            throw new IllegalArgumentException("网约车驾驶证识别OCR参数不能为空");
//        }
//        if (StringUtils.isBlank(ocrRequest.getImageBase64()) && StringUtils.isBlank(ocrRequest.getImageUrl())) {
//            throw new IllegalArgumentException("图片Base64值和图片Url不能同时为空");
//        }
//
//        log.info("rideHailingLicenseOcr 参数: " + JSON.toJSONString(ocrRequest));
//        //转换为腾讯云SDK OCR所需参数
//        RideHailingDriverLicenseOCRRequest tencentOCRReq = new RideHailingDriverLicenseOCRRequest();
//        if (StringUtils.isNotBlank(ocrRequest.getImageBase64())) {
//            tencentOCRReq.setImageBase64(ocrRequest.getImageBase64());
//        }
//        if (StringUtils.isNotBlank(ocrRequest.getImageUrl())) {
//            tencentOCRReq.setImageUrl(ocrRequest.getImageUrl());
//        }
//
//        RideHailingDriverLicenseOCRResponse tencentOCRRes = null;
//        try {
//            tencentOCRRes = ocrClient.RideHailingDriverLicenseOCR(tencentOCRReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "rideHailingLicenseOcr 调取腾讯云OCR失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//            throw new IllegalArgumentException(errorMsg);
//        }
//
//        log.info("rideHailingLicenseOcr 腾讯云SDK返回值: " + JSON.toJSONString(tencentOCRRes));
//        RideHailingLicenseOcrResponse ocrResponse = buildRidingLicenseOcrResponse(tencentOCRRes);
//
//        return ApiResult.success(ocrResponse, "网约车驾驶证识别成功");
//
//    }
//
//    /**
//     * 将腾讯云SDK返回值转换为自定义返回值，遵循驼峰命名
//     */
//    private RideHailingLicenseOcrResponse buildRidingLicenseOcrResponse(RideHailingDriverLicenseOCRResponse tencentOCRRes) {
//        RideHailingLicenseOcrResponse ocrResponse = new RideHailingLicenseOcrResponse();
//        BeanUtils.copyProperties(tencentOCRRes, ocrResponse);
//        return ocrResponse;
//    }
//
//    /**
//     * 网约车运输证识别
//     *
//     * @param ocrRequest
//     * @return
//     */
//    public ApiResult<RideHailingTransportOcrResponse> rideHailingTransportOcr(RideHailingTransportOcrRequest ocrRequest) {
//        if (ocrRequest == null) {
//            throw new IllegalArgumentException("网约车运输证识别OCR参数不能为空");
//        }
//        if (StringUtils.isBlank(ocrRequest.getImageBase64()) && StringUtils.isBlank(ocrRequest.getImageUrl())) {
//            throw new IllegalArgumentException("图片Base64值和图片Url不能同时为空");
//        }
//
//        log.info("rideHailingTransportOCR 参数: " + JSON.toJSONString(ocrRequest));
//        //转换为腾讯云SDK OCR所需参数
//        RideHailingTransportLicenseOCRRequest tencentOCRReq = new RideHailingTransportLicenseOCRRequest();
//        if (StringUtils.isNotBlank(ocrRequest.getImageBase64())) {
//            tencentOCRReq.setImageBase64(ocrRequest.getImageBase64());
//        }
//        if (StringUtils.isNotBlank(ocrRequest.getImageUrl())) {
//            tencentOCRReq.setImageUrl(ocrRequest.getImageUrl());
//        }
//
//        RideHailingTransportLicenseOCRResponse tencentOCRRes = null;
//        try {
//            tencentOCRRes = ocrClient.RideHailingTransportLicenseOCR(tencentOCRReq);
//        } catch (TencentCloudSDKException e) {
//            String errorMsg = "rideHailingTransportOCR 调取腾讯云OCR失败，requestId=" + e.getRequestId()
//                    + ", errorCode=" + e.getErrorCode() + ", msg=" + e.getMessage();
//            log.error(errorMsg + ", stackTrace=" + JSON.toJSONString(e.getStackTrace()));
//        }
//
//        log.info("rideHailingTransportOCR 腾讯云SDK返回值: " + JSON.toJSONString(tencentOCRRes));
//        RideHailingTransportOcrResponse ocrResponse = buildRidingTransportOcrResponse(tencentOCRRes);
//
//        //TODO 保存到数据库 不一定需要 看下具体需求
//        return ApiResult.success(ocrResponse, "网约车运输证OCR识别成功");
//    }
//
//    /**
//     * 将腾讯云SDK返回值转换为自定义返回值，遵循驼峰命名
//     */
//    private RideHailingTransportOcrResponse buildRidingTransportOcrResponse(RideHailingTransportLicenseOCRResponse tencentOCRRes) {
//        RideHailingTransportOcrResponse ocrResponse = new RideHailingTransportOcrResponse();
//        BeanUtils.copyProperties(tencentOCRRes, ocrResponse);
//        return ocrResponse;
//    }

}
