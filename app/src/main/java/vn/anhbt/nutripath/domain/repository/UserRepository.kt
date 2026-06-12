package vn.anhbt.nutripath.domain.repository

import vn.anhbt.nutripath.core.common.Resource
import vn.anhbt.nutripath.domain.model.UserProfile

interface UserRepository {

    suspend fun getUserProfile(userId: Long): Resource<UserProfile?>

    suspend fun updateUserProfile(userProfile: UserProfile): Resource<Unit>
}
