package vn.anhbt.nutripath.data.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.core.common.resourceOf
import vn.anhbt.nutripath.data.local.dao.UserDao
import vn.anhbt.nutripath.data.mapper.toDomain
import vn.anhbt.nutripath.data.mapper.toEntity
import vn.anhbt.nutripath.domain.model.UserProfile
import vn.anhbt.nutripath.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserProfile(userId: Long): Resource<UserProfile?> =
        resourceOf { userDao.getUserProfile(userId)?.toDomain() }

    override suspend fun updateUserProfile(userProfile: UserProfile): Resource<Unit> =
        resourceOf { userDao.update(userProfile.toEntity()) }
}
