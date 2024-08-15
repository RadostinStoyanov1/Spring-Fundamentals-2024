package com.philately.service;

import com.philately.model.dto.AddStampDTO;
import com.philately.model.dto.StampDTO;

import java.util.List;

public interface StampService {
    public boolean create(AddStampDTO addStampDTO);
    public List<StampDTO> findAllUploadedByUserStamps();
    public List<StampDTO> findAllOfferedStamps();
    public List<StampDTO> findAllWishListStamps();
    public List<StampDTO> findAllPurchasedStamps();
    public void moveStampToWishList(long id);
    void removeFromWishList(long id);
    void purchaseAllFromWishList();

}
