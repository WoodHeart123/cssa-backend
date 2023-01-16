package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface RentalService {
    Response getRental(ArrayList<String> productID);
}
