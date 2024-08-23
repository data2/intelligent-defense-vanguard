package com.data2.defense.core.service;

import com.data2.defense.core.dto.Pair;

import java.util.List;

public interface BaseService {

    boolean exists();

    boolean weakPassword();

    boolean unauthorizedAccess();

    boolean attack();
}
