package cn.ravanla.flash.dao.system;

import cn.ravanla.flash.bean.entity.system.FileInfo;
import cn.ravanla.flash.dao.BaseRepository;

public interface FileInfoRepository  extends BaseRepository<FileInfo,Long> {
    FileInfo findByRealFileName(String fileName);
}
