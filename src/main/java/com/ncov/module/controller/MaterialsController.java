package com.ncov.module.controller;

import com.ncov.module.common.SwaggerConstants;
import com.ncov.module.controller.dto.MaterialDto;
import com.ncov.module.controller.request.material.MaterialRequest;
import com.ncov.module.controller.resp.Page;
import com.ncov.module.controller.resp.RestResponse;
import com.ncov.module.controller.resp.material.MaterialResponse;
import com.ncov.module.security.UserContext;
import com.ncov.module.service.MaterialRequiredService;
import com.ncov.module.service.MaterialSuppliedService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MaterialsController {

    private final UserContext userContext;
    private final MaterialSuppliedService materialSuppliedService;
    private final MaterialRequiredService materialRequiredService;

    @ApiOperation(
            value = "Create new required material.",
            tags = SwaggerConstants.TAG_REQUIRED_MATERIALS
    )
    @PreAuthorize("hasRole('ROLE_HOSPITAL')")
    @PostMapping("/required-materials")
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createRequiredMaterial(@RequestBody @Valid MaterialRequest material) {
        return RestResponse.getResp("保存成功", materialRequiredService.saveRequiredInfo(material,
                userContext.getOrganisationId(), userContext.getUserId()));
    }

    @ApiOperation(
            value = "List required materials.",
            tags = SwaggerConstants.TAG_REQUIRED_MATERIALS
    )
    @GetMapping("/required-materials")
    @ResponseStatus(HttpStatus.OK)
    public Page<MaterialResponse> listRequiredMaterials(
            @RequestParam Integer page, @RequestParam Integer size,
            @RequestParam(name = "category", required = false) String category) {
        return materialRequiredService.getRequiredPageList(page, size, category);
    }

    @ApiOperation(
            value = "List my required materials.",
            tags = SwaggerConstants.TAG_REQUIRED_MATERIALS
    )
    @GetMapping("/required-materials/me")
    public Page<MaterialResponse> listMyRequiredMaterials(@RequestParam Integer page,
                                                          @RequestParam Integer size) {
        // TODO: 2020-01-29
        return Page.<MaterialResponse>builder()
                .page(page)
                .pageSize(size)
                .total(2L)
                .data(Arrays.asList(
                        MaterialResponse.builder()
                                .id(1L)
                                .material(MaterialDto.builder()
                                        .name("N95口罩")
                                        .category("口罩")
                                        .quantity(100000.0)
                                        .standard("ISO-8859-1")
                                        .build())
                                .address("湖北省武汉市东西湖区银潭路1号")
                                .contactorName("张三")
                                .contactorPhone("18801234567")
                                .comment("医护人员急用")
                                .status("PUBLISHED")
                                .gmtCreated(new Date())
                                .gmtModified(new Date())
                                .build(),
                        MaterialResponse.builder()
                                .id(2L)
                                .material(MaterialDto.builder()
                                        .name("医用防护服")
                                        .category("防护服")
                                        .quantity(2000.0)
                                        .standard("ISO-8859-10")
                                        .build())
                                .address("湖北省武汉市东西湖区银潭路1号")
                                .contactorName("张三")
                                .contactorPhone("18801234567")
                                .comment("医护人员急用")
                                .status("PUBLISHED")
                                .gmtCreated(new Date())
                                .gmtModified(new Date())
                                .build()
                ))
                .build();
    }

    @ApiOperation(
            value = "Create new supplied material.",
            tags = SwaggerConstants.TAG_SUPPLIED_MATERIALS
    )
    @PostMapping("/supplied-materials")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public RestResponse<List<MaterialResponse>> createSuppliedMaterial(@RequestBody @Valid MaterialRequest material) {
        List<MaterialResponse> responses = materialSuppliedService.create(material,
                userContext.getOrganisationId(), userContext.getUserId());
        return RestResponse.getResp("Supplied materials created.", responses);
    }

    @ApiOperation(
            value = "List supplied materials.",
            tags = SwaggerConstants.TAG_SUPPLIED_MATERIALS
    )
    @GetMapping("/supplied-materials")
    @ResponseStatus(HttpStatus.OK)
    public Page<MaterialResponse> listSuppliedMaterials(
            @RequestParam Integer page, @RequestParam Integer size,
            @RequestParam(name = "category", required = false) String category) {
        return materialSuppliedService.getSuppliedPageList(page, size, category);
    }

    @ApiOperation(
            value = "List my supplied materials.",
            tags = SwaggerConstants.TAG_SUPPLIED_MATERIALS
    )
    @GetMapping("/supplied-materials/me")
    public Page<MaterialResponse> listMySuppliedMaterials(@RequestParam Integer page,
                                                          @RequestParam Integer size) {
        // TODO: 2020-01-29
        return Page.<MaterialResponse>builder()
                .page(page)
                .pageSize(size)
                .total(2L)
                .data(Arrays.asList(
                        MaterialResponse.builder()
                                .id(1L)
                                .material(MaterialDto.builder()
                                        .name("N95口罩")
                                        .category("口罩")
                                        .quantity(100000.0)
                                        .standard("ISO-8859-1")
                                        .build())
                                .address("湖北省武汉市东西湖区银潭路1号")
                                .contactorName("张三")
                                .contactorPhone("18801234567")
                                .comment("医护人员急用")
                                .status("PUBLISHED")
                                .gmtCreated(new Date())
                                .gmtModified(new Date())
                                .build(),
                        MaterialResponse.builder()
                                .id(2L)
                                .material(MaterialDto.builder()
                                        .name("医用防护服")
                                        .category("防护服")
                                        .quantity(2000.0)
                                        .standard("ISO-8859-10")
                                        .build())
                                .address("湖北省武汉市东西湖区银潭路1号")
                                .contactorName("张三")
                                .contactorPhone("18801234567")
                                .comment("医护人员急用")
                                .status("PUBLISHED")
                                .gmtCreated(new Date())
                                .gmtModified(new Date())
                                .build()
                ))
                .build();
    }
}
