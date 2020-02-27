package db.springTempProc;

import lombok.Data;

import java.util.Map;

@Data
public class CommonResultDTO<T> {
    private String oresult;
    private String oerrinfo;
    Map data;


    public void vaild() {
//        if (null != this.oerrinfo && StringUtils.isNotBlank(this.oerrinfo)) {
//            throw new ApiException(this.oerrinfo);
        }


}
