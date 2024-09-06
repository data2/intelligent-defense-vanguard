package com.data2.defense.core.service;

import com.data2.defense.core.dto.Pair;

import java.util.ArrayList;
import java.util.List;

public class ParentService extends IpPortCheckerService {

    protected List<Pair> getNamePwd(List<String> names, List<String> pwds) {
        List<Pair> collection = new ArrayList<>();
        for (String name : names) {
            for (String pwd : pwds) {
                collection.add(new Pair(name, pwd));
            }
        }
        return collection;
    }
}
